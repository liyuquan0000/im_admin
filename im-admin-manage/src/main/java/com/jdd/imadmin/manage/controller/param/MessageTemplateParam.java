package com.jdd.imadmin.manage.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MessageTemplateParam implements Serializable {

    @ApiModelProperty(value = "模板编码")
    private String templateCode;

}
