package com.jdd.imadmin.manage.controller.param.oauth2Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UpdateWebServerRedirectUri implements Serializable {

    @ApiModelProperty(value = "技术提供商编号")
    private String clientNumber;

    @ApiModelProperty(value = "通知地址配置")
    private String webServerRedirectUri;
}
