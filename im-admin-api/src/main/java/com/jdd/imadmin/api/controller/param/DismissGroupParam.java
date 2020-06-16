package com.jdd.imadmin.api.controller.param;

import javax.validation.constraints.NotEmpty;

public class DismissGroupParam extends BaseParam {

    @NotEmpty(message="token 不能为空!")
    private String token;
    @NotEmpty(message="groupOpenId 不能为空!")
    private String groupOpenId;
    @NotEmpty(message="merchantOpenId 不能为空!")
    private String merchantOpenId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroupOpenId() {
        return groupOpenId;
    }

    public void setGroupOpenId(String groupOpenId) {
        this.groupOpenId = groupOpenId;
    }

    public String getMerchantOpenId() {
        return merchantOpenId;
    }

    public void setMerchantOpenId(String merchantOpenId) {
        this.merchantOpenId = merchantOpenId;
    }
}
