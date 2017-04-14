package me.cxis.mini.dubbo.support;

import java.io.Serializable;

/**
 * Created by cheng.xi on 2017-04-14 14:56.
 * 响应体
 */
public class Response implements Serializable{
    //结果
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
