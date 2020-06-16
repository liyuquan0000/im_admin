package com.jdd.imadmin.dao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * oauth2_code
 */
@Table(name = "oauth2_code")
public class Oauth2Code implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column( name = "n_id")
    private Long id;

    /**
     * 用户uuid
     */
    @Column( name = "s_user_id")
    private String userId;

    /**
     * 客户端id
     */
    @Column( name = "s_client_id")
    private String clientId;

    /**
     * 授权码
     */
    @Column( name = "s_code")
    private String code;

    /**
     * 创建时间
     */
    @Column( name = "d_create_time")
    private Date createTime;

    /**
     * 授权码失效时间
     */
    @Column( name = "d_expire_time")
    private Date expireTime;

    /**
     * oauth2_code
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}