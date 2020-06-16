package com.jdd.imadmin.manage.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class Oauth2TokenModel implements Serializable {

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
     * 有效时间
     */
    @ApiModelProperty(value = "有效时间")
    private Date accessTokenExpireTime;
}
