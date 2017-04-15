package me.cxis.mini.dubbo.provider;

import me.cxis.mini.dubbo.protocol.MiniDubboProtocol;
import me.cxis.mini.dubbo.protocol.Protocol;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cheng.xi on 2017-04-14 10:34.
 * 服务提供者
 */
public class DubboProvider<T> {
    //服务监听的端口
    private int port = 3347;

    //接口
    private Class<?> interfaceClass;

    //接口名字
    private String interfaceName;

    //接口的实现引用
    private T ref;

    //导出的服务，接口作为key，实现作为value
    private ConcurrentHashMap<String,Class<?>> exportedServices = new ConcurrentHashMap();

    public DubboProvider() {

    }

    /**
     * 发布服务，每次只发布一个服务
     */
    public synchronized void export() throws InterruptedException {
        //调用协议层，根据具体协议暴露服务
        Protocol protocol = MiniDubboProtocol.getMiniDubboProtocol();
        protocol.export(this.interfaceName,this.ref.getClass());
        //发布的服务缓存起来
        //exportedServices.put(this.interfaceName,this.ref.getClass());

    }



    //-------------以下是get和set方法-------------//
    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterface(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
        setInterface(interfaceClass == null ? (String) null : interfaceClass.getName());
    }

    public String getInterface() {
        return interfaceName;
    }

    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }
}
