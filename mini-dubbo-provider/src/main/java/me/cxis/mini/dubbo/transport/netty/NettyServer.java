package me.cxis.mini.dubbo.transport.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import me.cxis.mini.dubbo.transport.Server;

import java.net.InetSocketAddress;

/**
 * Created by cheng.xi on 2017-04-14 13:52.
 * Netty服务端
 */
public class NettyServer implements Server{

    private String address;
    private int port;
    private ChannelHandler handler;

    public NettyServer(String address, int port, ChannelHandler handler) throws InterruptedException {
        this.address = address;
        this.port= port;
        this.handler = handler;
        doOpen();
    }

    public void doOpen() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new ObjectDecoder(1024*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                    pipeline.addLast(new ObjectEncoder());
                    pipeline.addLast((SimpleChannelInboundHandler)handler);
                }
            });
            serverBootstrap.option(ChannelOption.SO_BACKLOG,1024);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture future = serverBootstrap.bind(address,port).sync();
            //future.channel().closeFuture().sync();
        }finally{
            //workerGroup.shutdownGracefully();
            //bossGroup.shutdownGracefully();
        }
    }

}
