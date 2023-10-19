package fun.pullock.mini.dubbo.extension.spi;

import fun.pullock.mini.dubbo.extension.ExtensionLoader;
import fun.pullock.mini.dubbo.extension.Transporter;
import org.junit.Test;

public class ExtensionLoaderTest {

    Transporter transporter = ExtensionLoader.getExtensionLoader(Transporter.class).getExtension("mina");

    @Test
    public void testExtensionLoader() {
        String url = "dubbo://127.0.0.1:20880";
        System.out.println(transporter.connect(url));
    }
}
