package com.jdd.imadmin.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * oauth2_user_token
 */
@Table(name = "oauth2_user_token")
public class Oauth2UserToken implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column( name = "n_id")
    private Long id;

    /**
     * IM用户id
     */
    @Column( name = "s_user_id")
    private String userId;

    /**
     * 用户uuid
     */
    @Column( name = "s_open_id")
    private String openId;

    /**
     * 客户端id
     */
    @Column( name = "s_client_id")
    private String clientId;

    /**
     * token令牌
     */
    @Column( name = "s_access_token")
    private String accessToken;

    /**
     * 用于刷新accessToken
     */
    @Column( name = "s_refresh_token")
    private String refreshToken;

    /**
     * 有效时间
     */
    @Column( name = "d_access_token_expire_time")
    private Date accessTokenExpireTime;

    /**
     * 有效时间, 一定要比accessToken的有效时间大
     */
    @Column( name = "d_refresh_token_expire_time")
    private Date refreshTokenExpireTime;

    /**
     * oauth2_user_token
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public Date getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(Date accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    public Date getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(Date refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }
}