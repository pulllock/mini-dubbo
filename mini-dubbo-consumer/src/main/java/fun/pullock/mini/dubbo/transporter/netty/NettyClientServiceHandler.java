package fun.pullock.mini.dubbo.transporter.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import fun.pullock.mini.dubbo.support.Response;

/**
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
