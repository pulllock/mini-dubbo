package me.cxis.mini.dubbo.transport;

import me.cxis.mini.dubbo.transport.netty.NettyServer;

import java.util.Map;

/**
 * Created by cheng.xi on 2017-04-14 13:40.
 * 传输层方便的工具类
 */
public class Transporters {
    public static void bindAndStart(Map<String, Class<?>> exportedServices) throws InterruptedException {
        new NettyServer().bindAndStart(exportedServices);
    }
}
