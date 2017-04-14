package me.cxis.mini.dubbo.consumer;

import me.cxis.mini.dubbo.proxy.DubboConsumerProxy;

/**
 * Created by cheng.xi on 2017-04-14 17:30.
 * 服务消费者端，获取代理
 */
public class DubboConsumer<T> {
    //接口名字
    private String interfaceName;

    //接口
    private Class<?> interfaceClass;

    //代理类
    private T ref;

    public T get(){
        //获取代理
        ref = new DubboConsumerProxy(interfaceClass).getProxy();
        return ref;
    }



    //--------------------以下是get和set方法----------------//
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setInterface(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
        setInterface(interfaceClass == null ? (String) null : interfaceClass.getName());
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
