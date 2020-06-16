package com.jdd.imadmin.manage.controller.model.oauth2Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public
class ClientDownModel implements Serializable {

    @ApiModelProperty(value = "编号")
    private String clientNumber;

    @ApiModelProperty(value = "简称")
    private String clientShortName;
}
