package com.jdd.imadmin.dao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * im_version
 */
@Table(name = "im_version")
public class ImVersion implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column( name = "n_id")
    private Long id;

    /**
     * 平台类型 1 IOS 2 Android
     */
    @Column( name = "n_platform")
    private Integer platform;

    /**
     * 版本号Code
     */
    @Column( name = "n_version_code")
    private Integer versionCode;

    /**
     * 当前版本号
     */
    @Column( name = "s_version")
    private String version;

    /**
     * 当前版本描述
     */
    @Column( name = "s_desc")
    private String desc;

    /**
     * 更新地址
     */
    @Column( name = "s_update_url")
    private String updateUrl;

    /**
     * 类型 1提示更新 2强制更新
     */
    @Column( name = "n_type")
    private Integer type;

    /**
     * 当前状态 1启用 0未启用
     */
    @Column( name = "n_status")
    private Integer status;

    /**
     * 更新时间
     */
    @Column( name = "d_modify_time")
    private Date modifyTime;

    /**
     * 更新人
     */
    @Column( name = "s_modify_by")
    private String modifyBy;

    /**
     * im_version
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
}