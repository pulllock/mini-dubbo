package me.cxis.mini.dubbo.transport;

import me.cxis.mini.dubbo.transport.netty.ChannelHandler;
import me.cxis.mini.dubbo.transport.netty.NettyServer;

import java.util.Map;

/**
 * Created by cheng.xi on 2017-04-14 13:40.
 * 传输层方便的工具类
 */
public class Transporters {

    //绑定，监听
    public static Server bind(String address, int port, ChannelHandler handler) throws InterruptedException {
        Server server = new NettyServer(address,port,handler);
        return server;
    }
}
