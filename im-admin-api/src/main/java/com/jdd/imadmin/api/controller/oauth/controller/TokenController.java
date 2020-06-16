package com.jdd.imadmin.api.controller.oauth.controller;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.oauth.vo.OauthTokenInfo;
import com.jdd.imadmin.common.util.LogExceptionStackTrace;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.service.auth2.OAuth2Service;
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
 * 技术提供商token令牌生成
 */
@SuppressWarnings("unchecked")
@RestController
@Slf4j
@RequestMapping("oauth2/tp")
public class TokenController {

    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private Oauth2ClientService oauth2ClientService;

    @RequestMapping("/token")
    public Object userAccessToken(HttpServletRequest request) {
        String clientId = "";
        String clientSecret = "";
        try {
            // 转为OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            clientId = oauthRequest.getClientId();
            clientSecret = oauthRequest.getClientSecret();

            //todo ip白名单校验

            //authorization_code 类型, 客户端认证模式
            if (!oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.CLIENT_CREDENTIALS.toString())) {
                return ResultModel.newErrorModel("grant type must be client_credentials");
            }

            //检查提交的客户端ID是否正确
            Oauth2Client oauth2Client = oauth2ClientService.getClientInfo(clientId, clientSecret);
            if (oauth2Client == null) {
                log.error("[获取accessToken时，客户端签名错误] clientId:{} clientSecret:{}", clientId, clientSecret);
                return ResultModel.newErrorModel("invalid client id or client secret");
            }

            Oauth2Token oauth2Token = generatorToken(oauth2Client);
            oAuth2Service.addAccessToken(oauth2Token);

            OauthTokenInfo oauthTokenInfo = convertToken(oauth2Token, oauth2Client);
            return ResultModel.newErrorModel(oauthTokenInfo);
        } catch (OAuthProblemException | OAuthSystemException e) {
            log.error("[获取accessToken发生异常] clientId:{} clientSecrete:{} exception:{}", clientId, clientSecret, LogExceptionStackTrace.erroStackTrace(e));
            return ResultModel.newErrorModel(e.getMessage());
        }
    }

    private OauthTokenInfo convertToken(Oauth2Token oauth2Token, Oauth2Client oauth2Client) {
        OauthTokenInfo oauthTokenInfo = new OauthTokenInfo();
        oauthTokenInfo.setToken(oauth2Token.getAccessToken());
        oauthTokenInfo.setTokenExpireIn(oauth2Client.getAccessTokenValidity());
        return oauthTokenInfo;
    }

    private Oauth2Token generatorToken(Oauth2Client oauth2Client) throws OAuthSystemException {
        Oauth2Token oauth2Token = new Oauth2Token();
        String clientId = oauth2Client.getClientId();
        oauth2Token.setClientId(clientId);

        // 生成Access Token
        OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
        String accessToken = oAuthIssuer.accessToken();
        oauth2Token.setAccessToken(accessToken);
        oauth2Token.setAccessTokenExpireTime(DateUtils.addSeconds(new Date(), oauth2Client.getAppAccessTokenValidity()));
        log.info("服务器生成的accessToken:{} clientId:{}", accessToken, clientId);

        return oauth2Token;
    }

}
