package com.jdd.imadmin.manage.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class VersionParam implements Serializable {

    @ApiModelProperty(value = "主键")
    private long id;
}
