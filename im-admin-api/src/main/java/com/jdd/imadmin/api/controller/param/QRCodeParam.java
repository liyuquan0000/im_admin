package com.jdd.imadmin.api.controller.param;

import javax.validation.constraints.NotEmpty;

public class QRCodeParam {

    @NotEmpty(message="token 不能为空!")
    private String token;

    @NotEmpty(message="key 不能为空!")
    private String key;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
