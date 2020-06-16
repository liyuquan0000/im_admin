package com.jdd.imadmin.manage.controller.model.oauth2Client;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class ListOauth2Client implements Serializable {

    @ApiModelProperty(value = "技术提供商编号")
    private String clientNumber;

    @ApiModelProperty(value = "技术提供商名称")
    private String clientName;

    @ApiModelProperty(value = "技术提供商简称")
    private String clientShortName;

    @ApiModelProperty(value = "开发者ID")
    private String clientId;

    @ApiModelProperty(value = "开发者密码")
    private String clientSecret;

    @ApiModelProperty(value = "通知地址")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date CreateTime;

}
