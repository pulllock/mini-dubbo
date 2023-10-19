package fun.pullock.mini.dubbo.extension;

@SPI("netty")
public interface Transporter {

    String connect(String url);
}
