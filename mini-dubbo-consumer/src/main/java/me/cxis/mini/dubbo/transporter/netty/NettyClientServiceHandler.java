package me.cxis.mini.dubbo.transporter.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.cxis.mini.dubbo.support.Response;

/**
 * Created by cheng.xi on 2017-04-14 14:31.
 * 服务消费者端的Netty消息处理器
 */
public class NettyClientServiceHandler extends SimpleChannelInboundHandler {
    private Response response;

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        response = (Response) object;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
