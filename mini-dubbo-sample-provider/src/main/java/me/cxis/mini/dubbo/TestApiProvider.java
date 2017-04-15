package me.cxis.mini.dubbo;

import me.cxis.mini.dubbo.consumer.DubboConsumer;
import me.cxis.mini.dubbo.provider.DubboProvider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * Created by cheng.xi on 2017-04-14 10:25.
 * 服务提供者端发布服务
 * 可以使用API直接发布服务，也可以使用和Spring集成
 * 这里是测试使用API发布服务
 */
public class TestApiProvider {
    public static void main(String[] args) throws InterruptedException {
        //要发布的服务
        HelloWorldService helloWorldService = new HelloWorldServiceImpl();

        //发布服务
        DubboProvider dubboProvider = new DubboProvider();
        dubboProvider.setPort(3347);
        dubboProvider.setRef(helloWorldService);
        dubboProvider.setInterface(HelloWorldService.class);

        dubboProvider.export();

        //要发布的服务
        HelloWorldService1 helloWorldService1 = new HelloWorldServiceImpl1();

        //发布服务
        DubboProvider dubboProvider1 = new DubboProvider();
        dubboProvider1.setPort(3347);
        dubboProvider1.setRef(helloWorldService1);
        dubboProvider1.setInterface(HelloWorldService1.class);

        dubboProvider1.export();

        DubboConsumer<HelloWorldService> reference = new DubboConsumer<HelloWorldService>();
        reference.setInterface(HelloWorldService.class);

        HelloWorldService helloWorldService2 = reference.get();
        System.out.println(helloWorldService2.sayHello("zhangsan"));
    }
}
