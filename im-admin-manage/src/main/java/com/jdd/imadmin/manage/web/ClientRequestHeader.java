package com.jdd.imadmin.manage.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ClientRequestHeader {
    @ApiModelProperty(value = "手机操作系统版本", example = "5.1")
    private String platformVersion;
    @ApiModelProperty(value = "手机系统类型", example = "ios, Android")
    private String platformCode;
    @ApiModelProperty(value = "app版本号", example = "100")
    private Integer appVersion;
    @ApiModelProperty(value = "token", example = "xxxx")
    private String token;
    @ApiModelProperty(value = "Base64类型userId", example = "MQ==")
    private String userId;
    @ApiModelProperty(value = "设备编码", example = "xxxxxx")
    private String uuid;
    @ApiModelProperty(value = "用户坐标", example = "15500000")
    private String ts;
    @ApiModelProperty(value = "手机机型", example = "xiaomi, iPhone")
    private String phoneName;
    @ApiModelProperty(value = "经度", example = "20.1")
    private double lng;
    @ApiModelProperty(value = "经度", example = "100.1")
    private double lat;
}
