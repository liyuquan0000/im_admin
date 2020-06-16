package com.jdd.imadmin.manage.controller.param.oauth2Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SaveImTechnologyProvider implements Serializable {

    @ApiModelProperty(value = "名称")
    private String clientName;

    @ApiModelProperty(value = "简称")
    private String clientShortName;
}
