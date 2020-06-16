package com.jdd.imadmin.manage.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@ApiModel
@Data
public class Oauth2UserTokenModel implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = " ")
    private Long userId;

    /**
     * 用户uuid
     */
    @ApiModelProperty(value = "用户uuid")
    private String openId;

    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    private String clientId;

    /**
     * token令牌
     */
    @ApiModelProperty(value = "token令牌")
    private String accessToken;

    /**
     * 用于刷新accessToken
     */
    @ApiModelProperty(value = "用于刷新accessToken")
    private String refreshToken;

    /**
     * 有效时间
     */
    @ApiModelProperty(value = "有效时间")
    private Date accessTokenExpireTime;

    /**
     * 有效时间, 一定要比accessToken的有效时间大
     */
    @ApiModelProperty(value = "有效时间, 一定要比accessToken的有效时间大")
    private Date refreshTokenExpireTime;
}
