package com.jdd.imadmin.dao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * im_auto_reply
 */
@Table(name = "im_auto_reply")
public class ImAutoReply implements Serializable {
    /**
     * 
     */
    @Id
    @Column( name = "n_id")
    private Long id;

    /**
     * 关联服务号id
     */
    @Column( name = "n_service_id")
    private Long serviceId;

    /**
     * 关联服务号openid
     */
    @Column( name = "s_service_open_id")
    private String serviceOpenId;

    /**
     * 自动回复类型
     */
    @Column( name = "s_content")
    private String content;

    /**
     * 关键词
     */
    @Column( name = "s_keyword")
    private String keyword;

    /**
     * 恢复类型
     */
    @Column( name = "s_content_type")
    private Integer contentType;

    /**
     * 当前状态 1启用 0停用
     */
    @Column( name = "n_status")
    private Integer status;

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
     * im_auto_reply
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceOpenId() {
        return serviceOpenId;
    }

    public void setServiceOpenId(String serviceOpenId) {
        this.serviceOpenId = serviceOpenId == null ? null : serviceOpenId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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