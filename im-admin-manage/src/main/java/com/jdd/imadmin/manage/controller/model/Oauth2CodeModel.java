package com.jdd.imadmin.manage.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@ApiModel
@Data
public class Oauth2CodeModel implements Serializable {

    /**
     * 用户uuid
     */
    @ApiModelProperty(value = "用户uuid")
    private Long userId;

    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    private String clientId;

    /**
     * 授权码
     */
    @ApiModelProperty(value = "授权码")
    private String code;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 授权码失效时间
     */
    @ApiModelProperty(value = "授权码失效时间")
    private Date expireTime;
}
