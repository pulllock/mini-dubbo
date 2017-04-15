package me.cxis.mini.dubbo.protocol;

/**
 * Created by cheng.xi on 2017-04-15 22:11.
 * 协议接口
 */
public interface Protocol {
    /**
     * 暴露服务
     * @param interfaceName 接口名字
     * @param impl 接口的实现
     */
    void export(String interfaceName, Class<?> impl) throws InterruptedException;
}
