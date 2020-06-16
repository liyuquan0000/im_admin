package com.jdd.imadmin.api.service.impl;


import com.jdd.imadmin.api.service.TokenVerifyService;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.service.auth2.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/************************************************************
 * @Description: 全局token校验
 * @Author: zhengrui
 * @Date 2020-02-25 10:35
 ************************************************************/

@Service
public class TokenVerifyServiceIml implements TokenVerifyService {

    @Autowired
    private OAuth2Service oAuth2Service;

    @Override
    public boolean verify(String accessToken) {
        Oauth2Token token = oAuth2Service.getTokenByAccessToken(accessToken);
        return token != null;
    }

    @Override
    public Oauth2Token getTokenDetail(String accessToken) {
        return oAuth2Service.getTokenByAccessToken(accessToken);
    }
}
