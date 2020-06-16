package com.jdd.imadmin.api.controller.oauth.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2019-08-01 19:21
 ************************************************************/

@Data
public class OauthUserTokenInfo {

    @JSONField(name = "open_id")
    private String openId;

    @JSONField(name = "client_id")
    private String clientId;

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "access_token_expire_in")
    private Integer accessTokenExpireIn;

    @JSONField(name = "refresh_token_expire_in")
    private Integer refreshTokenExpireIn;
}
