package fun.pullock.mini.dubbo;

public class HelloWorldServiceImpl1 implements HelloWorldService1 {
    public String sayHello(String name) {
        return "我接到你的消息了：" + name;
    }
}
