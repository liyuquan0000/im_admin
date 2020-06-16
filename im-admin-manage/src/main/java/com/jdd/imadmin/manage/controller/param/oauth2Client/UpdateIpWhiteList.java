package com.jdd.imadmin.manage.controller.param.oauth2Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UpdateIpWhiteList implements Serializable {

    @ApiModelProperty(value = "技术提供商编号")
    private String number;

    @ApiModelProperty(value = "IP白名单")
    private String ip;
}

