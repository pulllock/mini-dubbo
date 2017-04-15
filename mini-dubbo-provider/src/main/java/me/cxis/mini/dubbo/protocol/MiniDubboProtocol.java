package me.cxis.mini.dubbo.protocol;

import me.cxis.mini.dubbo.transport.Server;
import me.cxis.mini.dubbo.transport.Transporters;
import me.cxis.mini.dubbo.transport.netty.ChannelHandler;
import me.cxis.mini.dubbo.transport.netty.NettyServiceHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cheng.xi on 2017-04-15 22:11.
 * dubbo协议
 */
public class MiniDubboProtocol implements Protocol {
    //导出的服务
    private Map<String,Class<?>> exportedServices = new ConcurrentHashMap<String, Class<?>>();

    //打开的服务其
    private Map<String,Server> serverMap =new ConcurrentHashMap<String, Server>();
    private static Protocol miniDubboProtocol;

    public static Protocol getMiniDubboProtocol() {
        if(null == miniDubboProtocol){
            miniDubboProtocol = new MiniDubboProtocol();
        }
        return miniDubboProtocol;
    }

    public void export(String interfaceName, Class<?> impl) throws InterruptedException {
        //每个将每个接口的名字作为一个key，key用来作为对应实现的唯一键
        String key = interfaceName;
        exportedServices.put(key,impl);
        //打开服务其进行监听
        openServer();
        System.out.println("minidubboprotocol中：" + exportedServices.size());
    }

    private void openServer() throws InterruptedException {
        //服务器监听地址
        String address = "127.0.0.1";
        //端口号
        int port = 3347;
        //每个地址只打开一次
        String key = address + ":" + port;
        Server server = serverMap.get(key);
        if(null == server){
            //创建一个server
            serverMap.put(key,createServer(address,port));
        }
    }

    //创建server
    private Server createServer(String address, int port) throws InterruptedException {
        ChannelHandler handler = new NettyServiceHandler(exportedServices);
        Server server = Transporters.bind(address,port,handler);
        System.out.println("minidubboprotocol创建完server后：" + exportedServices.size());
        return server;
    }

}
