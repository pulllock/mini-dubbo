package me.cxis.mini.dubbo.extension.spi;

import me.cxis.mini.dubbo.extension.ExtensionLoader;
import me.cxis.mini.dubbo.extension.Transporter;
import org.junit.Test;

public class ExtensionLoaderTest {

    Transporter transporter = ExtensionLoader.getExtensionLoader(Transporter.class).getExtension("mina");

    @Test
    public void testExtensionLoader() {
        String url = "dubbo://127.0.0.1:20880";
        System.out.println(transporter.connect(url));
    }
}
