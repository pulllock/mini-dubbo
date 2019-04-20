package me.cxis.mini.dubbo.extension;

import me.cxis.mini.dubbo.utils.Holder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 使用一个ExtensionLoader：
 * 1. 想要获取哪个类型的ExtensionLoader
 * 2. 要获取的这个类型的具体哪个实现类
 * Xxx xxx = ExtensionLoader.getExtensionLoader(Xxx.class).getExtension("name");
 *
 *
 * 实现的功能：
 * 1. 根据指定的名字获取一个扩展的实现
 * 2. 能够获取一个自适应的扩展实现
 * 3. 自包含
 * @param <T>
 */
public class ExtensionLoader<T> {

    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    private final Class<?> type;

    private String cachedDefaultName;

    private static final String DUBBO_DIRECTORY = "META-INF/dubbo/";

    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }

        if (!type.isInterface()) {
            throw new IllegalArgumentException();
        }

        if (!withExtensionAnnotation(type)) {
            throw new IllegalArgumentException();
        }

        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }



    private ExtensionLoader(Class<?> type) {
        this.type = type;
    }

    public T getExtension(String name) {
        // 从缓存中查询
        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }

        Object instance = holder.getValue();
        if (instance == null) {
            synchronized (holder) {
                if (instance == null) {
                    // 创建实例
                    instance = createExtension(name);
                    holder.setValue(instance);
                }
            }
        }

        return (T) instance;
    }

    private static <T> boolean withExtensionAnnotation(Class<T> type) {
        return type.isAnnotationPresent(SPI.class);
    }

    private T createExtension(String name) {
        // 先加载所有的扩展类，并缓存
        Map<String, Class<?>> extensionClasses = getExtensionClasses();
        Class<?> clazz = extensionClasses.get(name);

        if (clazz == null) {
            throw new RuntimeException("extension not found");
        }

        // 如果能找到实现类，就进行实例化，并缓存起来
        try {
            T instance = (T) EXTENSION_INSTANCES.get(clazz);
            if (instance == null) {
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            }

            return instance;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("extension instance error");
        }
    }

    private Map<String, Class<?>> getExtensionClasses() {
        /**
         * 加载类，需要考虑怎么加载。
         * 加载的类肯定先要缓存起来，所以这里还是处理缓存中的东西。
         * 真正的加载还是要放到单独的方法中去。
         */
        Map<String, Class<?>> classes = cachedClasses.getValue();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.getValue();
                if (classes == null) {
                    classes = loadExtensionClasses();
                    cachedClasses.setValue(classes);
                }
            }
        }
        return classes;
    }

    private Map<String, Class<?>> loadExtensionClasses() {
        /**
         * 加载扩展类的实现类
         * 这里加载的是哪个类型的扩展的实现类？ 就是我们获取ExtensionLoader时指定的type
         * 要去哪里加载？得有默认的指定的目录
         */

        // 先看下注解的默认名字处理
        final SPI defaultAnnotation = type.getAnnotation(SPI.class);
        if (defaultAnnotation != null) {
            String value = defaultAnnotation.value();
            if ((value = value.trim()).length() > 0) {
                cachedDefaultName = value;
            }
        }

        // 开始从指定位置的配置文件中加载实现类
        Map<String, Class<?>> extensionClass = new HashMap<>();
        loadFile(extensionClass, DUBBO_DIRECTORY);
        return extensionClass;
    }

    private void loadFile(Map<String, Class<?>> extensionClass, String dir) {
        /**
         * 配置文件目录定义好了，在META-INF/dubbo下
         * 配置文件的定义：
         * 1. 配置文件的名称：接口权限定名
         * 2. 配置文件的定义：name = 实现类的权限定名
         */

        String fileName = dir + type.getName();

        // 开始加载所有的文件

        try {
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();

            Enumeration<java.net.URL> urls;

            if (classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }

            if (urls != null) {
                while (urls.hasMoreElements()) {
                    java.net.URL url = urls.nextElement();

                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

                        try {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                final int commentIndex = line.indexOf('#');
                                if (commentIndex > 0) {
                                    line = line.substring(0, commentIndex);

                                    line = line.trim();
                                }

                                if (line.length() > 0) {

                                    try {
                                        String name = null;
                                        int index = line.indexOf('=');

                                        if (index > 0) {
                                            name = line.substring(0, index).trim();
                                            line = line.substring(index + 1).trim();
                                        }

                                        if (line.length() > 0) {
                                            Class<?> clazz = Class.forName(line, true, classLoader);

                                            if (!type.isAssignableFrom(clazz)) {
                                                throw new IllegalStateException();
                                            }

                                            extensionClass.putIfAbsent(name, clazz);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            reader.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
