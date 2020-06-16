package com.jdd.imadmin.manage.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@ApiModel
public class MessageTemplateModel implements Serializable {

    @ApiModelProperty(value = "模板编码")
    private String templateCode;

    @ApiModelProperty(value = "技术提供商编号")
    private String clientNumber;

    @ApiModelProperty(value = "技术提供商名称")
    private String clientName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "类型 1:打开链接")
    private Integer type;

    @ApiModelProperty(value = "目标内容")
    private String url;

}
