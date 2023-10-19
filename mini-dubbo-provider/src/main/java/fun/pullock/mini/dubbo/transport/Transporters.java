package fun.pullock.mini.dubbo.transport;

import fun.pullock.mini.dubbo.transport.netty.ChannelHandler;
import fun.pullock.mini.dubbo.transport.netty.NettyServer;

/**
 * 传输层方便的工具类
 */
public class Transporters {

    //绑定，监听
    public static Server bind(String address, int port, ChannelHandler handler) throws InterruptedException {
        Server server = new NettyServer(address,port,handler);
        return server;
    }
}
