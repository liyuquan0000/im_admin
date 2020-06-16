package com.jdd.imadmin.dao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * im_message_template
 */
@Table(name = "im_message_template")
public class ImMessageTemplate implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column( name = "n_id")
    private Long id;

    /**
     * 模板code
     */
    @Column( name = "s_template_code")
    private String templateCode;

    /**
     * 关联技术提供商
     */
    @Column( name = "s_client_number")
    private String clientNumber;

    /**
     * 标题
     */
    @Column( name = "s_title")
    private String title;

    /**
     * 模板内容
     */
    @Column( name = "n_content")
    private String content;

    /**
     * 模板类型 1文本
     */
    @Column( name = "n_template_type")
    private Integer templateType;

    /**
     * 状态 1有效 0无效
     */
    @Column( name = "n_status")
    private Integer status;

    /**
     * 类型 1打开链接
     */
    @Column( name = "n_type")
    private Integer type;

    /**
     * 跳转url
     */
    @Column( name = "s_url")
    private String url;

    /**
     * 创建时间
     */
    @Column( name = "d_create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column( name = "d_update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @Column( name = "s_create_by")
    private String createBy;

    /**
     * 更新人
     */
    @Column( name = "s_update_by")
    private String updateBy;

    /**
     * im_message_template
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode == null ? null : templateCode.trim();
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}