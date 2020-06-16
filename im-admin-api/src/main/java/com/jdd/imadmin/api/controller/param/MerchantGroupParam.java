package com.jdd.imadmin.api.controller.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MerchantGroupParam {


    @NotEmpty(message = "token不能为空!")
    private String token;
    /**
     * 商户在技术服务商的唯一ID
     */
    @NotEmpty(message = "merchantOpenId不能为空!")
    private String merchantOpenId;
    @NotNull(message = "pageNo不能为空!")
    private Integer pageNo;
    @NotNull(message = "pageSize不能为空!")
    private Integer pageSize;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMerchantOpenId() {
        return merchantOpenId;
    }

    public void setMerchantOpenId(String merchantOpenId) {
        this.merchantOpenId = merchantOpenId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
