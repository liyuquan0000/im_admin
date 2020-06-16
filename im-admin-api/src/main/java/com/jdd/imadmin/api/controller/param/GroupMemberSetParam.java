package com.jdd.imadmin.api.controller.param;

import javax.validation.constraints.NotEmpty;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2020-03-03 11:22
 ************************************************************/

public class GroupMemberSetParam extends BaseParam {
    @NotEmpty(message="token 不能为空!")
    private String token;
    @NotEmpty(message="groupOpenId 不能为空!")
    private String groupOpenId;

    private Boolean isManager;
    @NotEmpty(message="adminOpenId 不能为空!")
    private String adminOpenId;
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

    public String getAdminOpenId() {
        return adminOpenId;
    }

    public void setAdminOpenId(String adminOpenId) {
        this.adminOpenId = adminOpenId;
    }

    public String getMerchantOpenId() {
        return merchantOpenId;
    }

    public void setMerchantOpenId(String merchantOpenId) {
        this.merchantOpenId = merchantOpenId;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }
}
