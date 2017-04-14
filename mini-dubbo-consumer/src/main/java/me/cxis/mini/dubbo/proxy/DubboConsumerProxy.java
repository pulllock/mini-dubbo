package me.cxis.mini.dubbo.proxy;

import me.cxis.mini.dubbo.support.Request;
import me.cxis.mini.dubbo.transporter.Transporters;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by cheng.xi on 2017-04-14 09:33.
 * 服务消费者端的代理，使用JDK动态代理
 */
public class DubboConsumerProxy implements InvocationHandler{

    private Class<?> interfaces;

    public DubboConsumerProxy(Class<?> interfaces){
        this.interfaces = interfaces;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(),new Class[]{interfaces},this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;

        //请求体
        Request request = new Request();
        request.setInterfaceName(interfaces.getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setArgs(args);

        //远程调用服务提供者
        result = Transporters.connectAndExecute(request);

        return result;
    }
}
