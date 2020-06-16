package com.jdd.imadmin.api.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VersionDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 平台类型 1 IOS 2 Android
     */
    private Integer platform;

    /**
     * 版本号Code
     */
    private Integer versionCode;

    /**
     * 当前版本号
     */
    private String version;

    /**
     * 当前版本描述
     */
    private String desc;

    /**
     * 更新地址
     */
    private String updateUrl;

    /**
     * 类型 1提示更新 2强制更新
     */
    private Integer type;

    /**
     * 当前状态 1启用 0未启用
     */
    private Integer status;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    /**
     * 更新人
     */
    private String modifyBy;
}
