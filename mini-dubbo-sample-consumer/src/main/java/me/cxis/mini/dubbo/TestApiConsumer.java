package me.cxis.mini.dubbo;

import me.cxis.mini.dubbo.consumer.DubboConsumer;

/**
 * Created by cheng.xi on 2017-04-14 09:28.
 * 服务消费者端引用服务
 */
public class TestApiConsumer {
    public static void main(String[] args) {

        DubboConsumer<HelloWorldService> reference = new DubboConsumer<HelloWorldService>();
        reference.setInterface(HelloWorldService.class);

        HelloWorldService helloWorldService = reference.get();
        System.out.println(helloWorldService.sayHello("zhangsan"));

    }
}
