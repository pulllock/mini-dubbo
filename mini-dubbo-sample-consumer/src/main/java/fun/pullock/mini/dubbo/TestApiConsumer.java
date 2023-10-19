package fun.pullock.mini.dubbo;

import fun.pullock.mini.dubbo.consumer.DubboConsumer;

/**
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
