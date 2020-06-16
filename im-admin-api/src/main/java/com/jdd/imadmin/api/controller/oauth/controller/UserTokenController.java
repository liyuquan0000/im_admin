package com.jdd.imadmin.api.controller.oauth.controller;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.oauth.vo.OauthUserTokenInfo;
import com.jdd.imadmin.common.util.LogExceptionStackTrace;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.dao.entity.Oauth2Code;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import com.jdd.imadmin.service.auth2.Oauth2ClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 令用户牌生成
 */
@SuppressWarnings("unchecked")
@RestController
@Slf4j
@RequestMapping("oauth2/user")
public class UserTokenController {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Autowired
    private Oauth2ClientService oauth2ClientService;

    @RequestMapping("/access_token")
    public Object userAccessToken(HttpServletRequest request) {
        String clientId = "";
        String clientSecret = "";
        try {
            // 转为OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            clientId = oauthRequest.getClientId();
            clientSecret = oauthRequest.getClientSecret();

            //检查提交的客户端ID是否正确
            Oauth2Client oauth2Client = oauth2ClientService.getClientInfo(clientId, clientSecret);
            if (oauth2Client == null) {
                log.error("[获取accessToken时，客户端签名错误] clientId:{} clientSecret:{}", clientId, clientSecret);
                return ResultModel.newErrorModel("invalid client id or client secret");
            }

            //authorization_code 类型, 授权码模式
            if (!oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                return ResultModel.newErrorModel("grant type must be authorization_code");
            }

            String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
            Oauth2Code oauthCodeInfo = oAuth2UserService.getOauthCodeInfo(authCode);
            if (oauthCodeInfo == null) {
                return ResultModel.newErrorModel("invalid code");
            }

            String userId = oauthCodeInfo.getUserId();
            Oauth2UserToken oauth2UserToken = generatorToken(oauth2Client, userId);
            oauth2UserToken = oAuth2UserService.addAccessToken(oauth2UserToken, authCode);

            OauthUserTokenInfo oauthUserTokenInfo = convertToken(oauth2UserToken, oauth2Client);
            return ResultModel.newSuccessModel(oauthUserTokenInfo);
        } catch (OAuthProblemException | OAuthSystemException e) {
            log.error("[获取accessToken发生异常] clientId:{} clientSecrete:{} exception:{}", clientId, clientSecret, LogExceptionStackTrace.erroStackTrace(e));
            return ResultModel.newErrorModel(e.getMessage());
        }
    }

    @RequestMapping("/refresh_token")
    public Object refreshToken(HttpServletRequest request) {
        String clientId = "";
        String clientSecret = "";
        String refreshToken = "";
        try {
            // 转为OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            refreshToken = oauthRequest.getRefreshToken();
            clientId = oauthRequest.getClientId();
            clientSecret = oauthRequest.getClientSecret();

            //检查提交的客户端ID是否正确
            Oauth2Client oauth2Client = oauth2ClientService.getClientInfo(clientId, clientSecret);
            if (oauth2Client == null) {
                log.error("[刷新accessToken时，客户端签名错误] clientId:{} clientSecret:{}", clientId, clientSecret);
                return ResultModel.newErrorModel("invalid client id or client secret");
            }

            //refresh_token 类型
            if (!oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString())) {
                return ResultModel.newErrorModel("grant type must be refresh_token");
            }

            Oauth2UserToken oauth2UserToken = oAuth2UserService.getTokenByRefreshToken(refreshToken);
            if (oauth2UserToken == null) {
                return ResultModel.refreshTokenInvalid();
            }

            String userId = oauth2UserToken.getUserId();
            Oauth2UserToken newOauth2Token = generatorToken(oauth2Client, userId);
            newOauth2Token = oAuth2UserService.addAccessToken(newOauth2Token);

            OauthUserTokenInfo oauthUserTokenInfo = convertToken(newOauth2Token, oauth2Client);
            return ResultModel.newSuccessModel(oauthUserTokenInfo);
        } catch (OAuthProblemException | OAuthSystemException e) {
            log.error("[refreshToken刷新accessToken发生异常] clientId:{} clientSecrete:{} refreshToken:{} exception:{}", clientId, clientSecret, refreshToken, LogExceptionStackTrace.erroStackTrace(e));
            return ResultModel.newErrorModel(e.getMessage());
        }
    }

    private OauthUserTokenInfo convertToken(Oauth2UserToken oauth2UserToken, Oauth2Client oauth2Client) {
        OauthUserTokenInfo oauthUserTokenInfo = new OauthUserTokenInfo();
        oauthUserTokenInfo.setClientId(oauth2UserToken.getClientId());
        oauthUserTokenInfo.setOpenId(oauth2UserToken.getOpenId());
        oauthUserTokenInfo.setAccessToken(oauth2UserToken.getAccessToken());
        oauthUserTokenInfo.setRefreshToken(oauth2UserToken.getRefreshToken());
        oauthUserTokenInfo.setAccessTokenExpireIn(oauth2Client.getAccessTokenValidity());
        oauthUserTokenInfo.setRefreshTokenExpireIn(oauth2Client.getRefreshTokenValidity());
        return oauthUserTokenInfo;
    }

    private Oauth2UserToken generatorToken(Oauth2Client oauth2Client, String userId) throws OAuthSystemException {
        Oauth2UserToken oauth2UserToken = new Oauth2UserToken();
        String clientId = oauth2Client.getClientId();
        oauth2UserToken.setClientId(clientId);
        oauth2UserToken.setUserId(userId);

        // 生成Access Token
        OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
        String accessToken = oAuthIssuer.accessToken();
        oauth2UserToken.setAccessToken(accessToken);
        oauth2UserToken.setAccessTokenExpireTime(DateUtils.addSeconds(new Date(), oauth2Client.getAccessTokenValidity()));
        log.info("服务器生成的accessToken:{} clientId:{}", accessToken, clientId);

        // 生成Refresh Token
        String refreshToken = oAuthIssuer.refreshToken();
        oauth2UserToken.setRefreshToken(refreshToken);
        oauth2UserToken.setRefreshTokenExpireTime(DateUtils.addSeconds(new Date(), oauth2Client.getRefreshTokenValidity()));
        log.info("服务器生成的refreshToken:{} clientId:{}", refreshToken, clientId);

        return oauth2UserToken;
    }

}
