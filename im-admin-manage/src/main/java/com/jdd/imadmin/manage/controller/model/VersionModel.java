package com.jdd.imadmin.manage.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class VersionModel implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "平台类型 1 IOS 2 Android")
    private Integer platform;

    @ApiModelProperty(value = "版本号Code")
    private Integer versionCode;

    @ApiModelProperty(value = "当前版本号")
    private String version;

    @ApiModelProperty(value = "当前版本描述")
    private String desc;

    @ApiModelProperty(value = "更新地址")
    private String updateUrl;

    @ApiModelProperty(value = "类型 1提示更新 2强制更新")
    private Integer type;

    @ApiModelProperty(value = "当前状态 1启用 0未启用")
    private Integer status;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date modifyTime;
}
