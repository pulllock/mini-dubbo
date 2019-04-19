package me.cxis.mini.dubbo.extension;

public class MinaTransporter implements Transporter {

    public String connect(String url) {
        System.out.println("use mina transporter connect to " + url);
        return "Mina transporter connected...";
    }
}
