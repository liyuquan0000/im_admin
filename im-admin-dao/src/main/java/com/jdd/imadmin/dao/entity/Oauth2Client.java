package com.jdd.imadmin.dao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * oauth2_client
 */
@Table(name = "oauth2_client")
public class Oauth2Client implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column( name = "n_id")
    private Long id;

    /**
     * 客户端编号
     */
    @Column( name = "s_client_number")
    private String clientNumber;

    /**
     * 客户端名称
     */
    @Column( name = "s_client_name")
    private String clientName;

    /**
     * 客户端简称
     */
    @Column( name = "s_client_short_name")
    private String clientShortName;

    /**
     * 客户端id
     */
    @Column( name = "s_client_id")
    private String clientId;

    /**
     * 资源地址,逗号拼接
     */
    @Column( name = "s_resources")
    private String resources;

    /**
     * 客户端秘钥
     */
    @Column( name = "s_client_secret")
    private String clientSecret;

    /**
     * 访问范围, 暂时不用
     */
    @Column( name = "s_scope")
    private String scope;

    /**
     * 授权类型, 目前只有authorization_code
     */
    @Column( name = "s_authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 授权登录后重定向第三方地址
     */
    @Column( name = "s_web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 支付回调地址
     */
    @Column( name = "s_web_server_pay_uri")
    private String webServerPayUri;

    /**
     * 用户accessToken有效期
     */
    @Column( name = "d_access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 用户refreshToken有效期
     */
    @Column( name = "d_refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 全局token有效期
     */
    @Column( name = "d_app_access_token_validity")
    private Integer appAccessTokenValidity;

    /**
     * ip白名单,多个ip回车分隔
     */
    @Column( name = "s_ip_white_list")
    private String ipWhiteList;

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
    @Column( name = "s_created_by")
    private String createdBy;

    /**
     * 更新人
     */
    @Column( name = "s_updated_by")
    private String updatedBy;

    /**
     * 有效性  1:有效 0:无效
     */
    @Column( name = "n_status")
    private Integer status;

    /**
     * oauth2_client
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getClientShortName() {
        return clientShortName;
    }

    public void setClientShortName(String clientShortName) {
        this.clientShortName = clientShortName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources == null ? null : resources.trim();
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret == null ? null : clientSecret.trim();
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes == null ? null : authorizedGrantTypes.trim();
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri == null ? null : webServerRedirectUri.trim();
    }

    public String getWebServerPayUri() {
        return webServerPayUri;
    }

    public void setWebServerPayUri(String webServerPayUri) {
        this.webServerPayUri = webServerPayUri == null ? null : webServerPayUri.trim();
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public Integer getAppAccessTokenValidity() {
        return appAccessTokenValidity;
    }

    public void setAppAccessTokenValidity(Integer appAccessTokenValidity) {
        this.appAccessTokenValidity = appAccessTokenValidity;
    }

    public String getIpWhiteList() {
        return ipWhiteList;
    }

    public void setIpWhiteList(String ipWhiteList) {
        this.ipWhiteList = ipWhiteList == null ? null : ipWhiteList.trim();
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}