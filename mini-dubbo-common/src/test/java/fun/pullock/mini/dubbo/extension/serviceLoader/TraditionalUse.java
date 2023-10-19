package fun.pullock.mini.dubbo.extension.serviceLoader;

import fun.pullock.mini.dubbo.extension.MinaTransporter;
import fun.pullock.mini.dubbo.extension.NettyTransporter;
import fun.pullock.mini.dubbo.extension.Transporter;

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
