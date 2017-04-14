package me.cxis.mini.dubbo;

import me.cxis.mini.dubbo.provider.DubboProvider;


/**
 * Created by cheng.xi on 2017-04-14 10:25.
 * 服务提供者端发布服务
 * 可以使用API直接发布服务，也可以使用和Spring集成
 * 这里是测试使用API发布服务
 */
public class TestApiProvider {
    public static void main(String[] args) {
        //要发布的服务
        HelloWorldService helloWorldService = new HelloWorldServiceImpl();

        //发布服务
        DubboProvider dubboProvider = new DubboProvider();
        dubboProvider.setPort(3347);
        dubboProvider.setRef(helloWorldService);
        dubboProvider.setInterface(HelloWorldService.class);

        dubboProvider.export();



    }
}
