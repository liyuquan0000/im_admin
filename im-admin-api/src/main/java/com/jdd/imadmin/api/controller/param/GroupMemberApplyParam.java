package com.jdd.imadmin.api.controller.param;

import com.jdd.imserver.pojos.PageRequest;

import javax.validation.constraints.NotEmpty;

public class GroupMemberApplyParam extends PageRequest {

    @NotEmpty(message="groupOpenId不能为空!")
    private String groupOpenId;
    @NotEmpty(message="merchantOpenId不能为空!")
    private String merchantOpenId;
    @NotEmpty(message="token不能为空!")
    private String token;

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
