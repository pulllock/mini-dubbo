package me.cxis.mini.dubbo.extension;

import me.cxis.mini.dubbo.utils.Holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 1. 根据指定的名字获取一个扩展的实现
 * 2. 能够获取一个自适应的扩展实现
 * 3. 自包含
 * @param <T>
 */
public class ExtensionLoader<T> {

    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

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
        // TODO
        return null;
    }
}
