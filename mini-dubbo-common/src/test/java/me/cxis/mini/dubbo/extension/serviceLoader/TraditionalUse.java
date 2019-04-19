package me.cxis.mini.dubbo.extension.serviceLoader;

import me.cxis.mini.dubbo.extension.MinaTransporter;
import me.cxis.mini.dubbo.extension.NettyTransporter;
import me.cxis.mini.dubbo.extension.Transporter;

public class TraditionalUse {

    public void useNettyConnect() {
        String url = "dubbo://127.0.0.1:20880";
        Transporter transporter = new NettyTransporter();
        String result = transporter.connect(url);
        System.out.println(result);
    }

    public void useMinaConnect() {
        String url = "dubbo://127.0.0.1:20880";
        Transporter transporter = new MinaTransporter();
        String result = transporter.connect(url);
        System.out.println(result);
    }

    public static void main(String[] args) {
        TraditionalUse traditionalUse = new TraditionalUse();
        traditionalUse.useMinaConnect();
        traditionalUse.useNettyConnect();
    }
}
