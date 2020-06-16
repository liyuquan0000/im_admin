package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class UserMerchantParam implements Serializable {

    /**
     * token
     */
    @NotEmpty(message = "token 不能为空!")
    private String token;


    /**
     * 商户openId
     */
    @NotEmpty(message = "merchantOpenId 不能为空!")
    private String merchantOpenId;


    /**
     * 用户openId
     */
    private String openId;


    /**
     * 状态 0 取消拉黑 1拉黑
     */
    @NotNull(message = "拉黑状态不能为空")
    private Integer status;


}