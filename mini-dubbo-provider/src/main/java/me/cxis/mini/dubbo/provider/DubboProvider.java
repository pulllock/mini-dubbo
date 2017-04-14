package me.cxis.mini.dubbo.provider;

import me.cxis.mini.dubbo.handler.ServiceHandler;
import me.cxis.mini.dubbo.transport.Transporters;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    //接口的引用
    private T ref;

    //导出的服务，接口作为key，实现作为value
    private ConcurrentHashMap<String,Class<?>> exportedServices = new ConcurrentHashMap();

    public DubboProvider() {

    }

    /**
     * 发布服务
     */
    public void export() {
        //发布的服务缓存起来
        exportedServices.put(this.interfaceName,this.ref.getClass());

        //启动服务监听
        this.startServerAndListening();
    }

    //启动服务，并监听
    private void startServerAndListening() {
        //调用传输层，进行服务监听
        try {
            Transporters.bindAndStart(exportedServices);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
