package me.cxis.mini.dubbo.support;

import java.io.Serializable;

/**
 * Created by cheng.xi on 2017-04-14 14:56.
 * 请求体
 */
public class Request implements Serializable{
    //接口名
    private String interfaceName;
    //方法名
    private String methodName;
    //参数类型
    private Class<?>[] parameterTypes;
    //参数
    private Object[] args;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
