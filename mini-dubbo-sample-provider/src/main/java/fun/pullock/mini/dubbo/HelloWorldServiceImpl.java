package fun.pullock.mini.dubbo;

public class HelloWorldServiceImpl implements HelloWorldService {
    public String sayHello(String name) {
        return "我接到你的消息了：" + name;
    }
}
