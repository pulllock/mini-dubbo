package fun.pullock.mini.dubbo.transporter;

import fun.pullock.mini.dubbo.support.Request;
import fun.pullock.mini.dubbo.transporter.netty.NettyClient;
import fun.pullock.mini.dubbo.transporter.netty.NettyClientServiceHandler;

import java.util.concurrent.ExecutionException;

public class Transporters {
    public static Object connectAndExecute(Request request) throws ExecutionException, InterruptedException {
        //处理器，用来处理返回的消息
        NettyClientServiceHandler handler = new NettyClientServiceHandler();
        //使用nettyclient来进行连接服务提供者和执行请求
        new NettyClient().connectAndExecute(request,handler);
        //从处理器中获取返回的消息
        return handler.getResponse().getResult();
    }
}
