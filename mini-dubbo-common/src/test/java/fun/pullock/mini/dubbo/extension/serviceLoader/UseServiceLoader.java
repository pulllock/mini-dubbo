package fun.pullock.mini.dubbo.extension.serviceLoader;

import fun.pullock.mini.dubbo.extension.MinaTransporter;
import fun.pullock.mini.dubbo.extension.NettyTransporter;
import fun.pullock.mini.dubbo.extension.Transporter;

import java.util.ServiceLoader;

public class UseServiceLoader {

    private static ServiceLoader<Transporter> serviceLoader = ServiceLoader.load(Transporter.class);

    public static Transporter getTransporter(Class<?> type) {
        for (Transporter transporter : serviceLoader) {
            if (type.isAssignableFrom(transporter.getClass())) {
                return transporter;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "dubbo://127.0.0.1:20880";
        Transporter transporter = UseServiceLoader.getTransporter(NettyTransporter.class);
        System.out.println(transporter.connect(url));

        transporter = UseServiceLoader.getTransporter(MinaTransporter.class);
        System.out.println(transporter.connect(url));
    }
}
