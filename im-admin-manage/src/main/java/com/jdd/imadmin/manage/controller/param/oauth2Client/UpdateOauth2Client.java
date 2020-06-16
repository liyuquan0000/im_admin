package com.jdd.imadmin.manage.controller.param.oauth2Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class UpdateOauth2Client implements Serializable {

    @ApiModelProperty(value = "客户端编号")
    private String clientNumber;

    @ApiModelProperty(value = "客户端名称")
    private String clientName;

    @ApiModelProperty(value = "客户端简称")
    private String clientShortName;

    @ApiModelProperty(value = "客户端id(开发者ID)")
    private String clientId;

    @ApiModelProperty(value = "资源地址,逗号拼接")
    private String resources;

    @ApiModelProperty(value = "客户端秘钥(开发者密码)")
    private String clientSecret;

    @ApiModelProperty(value = "访问范围, 暂时不用")
    private String scope;

    @ApiModelProperty(value = "授权类型, 目前只有authorization_code")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "授权登录后重定向第三方地址(通知地址)")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "支付回调地址")
    private String webServerPayUri;

    @ApiModelProperty(value = "用户accessToken有效期")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "用户refreshToken有效期")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "全局token有效期")
    private Integer appAccessTokenValidity;

    @ApiModelProperty(value = "ip白名单,多个ip回车分隔")
    private String ipWhiteList;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "更新人")
    private String updatedBy;

    @ApiModelProperty(value = "有效性  1:有效 0:无效")
    private Integer status;
}
