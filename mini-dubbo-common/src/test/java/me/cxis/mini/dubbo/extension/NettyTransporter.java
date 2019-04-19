package me.cxis.mini.dubbo.extension;

public class NettyTransporter implements Transporter {

    public String connect(String url) {
        System.out.println("use netty transporter connect to " + url);
        return "Netty transporter connected...";
    }
}
