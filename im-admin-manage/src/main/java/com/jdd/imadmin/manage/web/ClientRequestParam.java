package com.jdd.imadmin.manage.web;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zhangdi
 * @date 17/1/19
 * @Description
 */
public class ClientRequestParam<T> {
    private ClientRequestHeader header;
    private T body;


    public ClientRequestHeader getHeader() {
        return header;
    }

    public void setHeader(ClientRequestHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public <T> T getBody(Class<T> clazz) {
        return JSONObject.parseObject(String.valueOf(body),clazz);
    }
}
