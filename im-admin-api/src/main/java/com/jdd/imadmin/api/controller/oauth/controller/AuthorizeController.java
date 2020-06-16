package com.jdd.imadmin.api.controller.oauth.controller;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.oauth.constant.Oauth2Constants;

import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import com.jdd.imadmin.service.auth2.Oauth2ClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 授权处理类
 */
@RestController
@Slf4j
@RequestMapping("oauth2/")
public class AuthorizeController {

    @Autowired
    private Oauth2ClientService oauth2ClientService;

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @RequestMapping("/authorize")
    public Object authroize(HttpServletRequest request) throws OAuthSystemException, URISyntaxException {

        try {
            // 构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

            // 检查传入的客户端ID是否正确
            String clientId = oauthRequest.getClientId();
            Oauth2Client clientInfo = oauth2ClientService.findByClientId(clientId);
            if (clientInfo == null) {
                log.error("校验客户端ID失败，ClientID=" + clientId);
                return ResultModel.newErrorModel("invalid client id");
            }

            //获取用户信息
            String userId = oauthRequest.getParam(Oauth2Constants.OAUTH_USER_ID);

            if (userId == null) {
                log.error("用户id为空");
                return ResultModel.newErrorModel("invalid user_id");
            }

            // 生成授权码
            String authorizationCode = null;
            // resopnseType 目前仅支持CODE， 另外还有TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);

            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oAuthIssuer.authorizationCode();
                log.info("服务端生成的授权码=" + authorizationCode);
                oAuth2UserService.addAuthCode(authorizationCode, userId, clientId);
            } else {
                return ResultModel.newErrorModel("invalid response_type");
            }

            // 构建OAuth响应
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);

            // 设置授权码
            builder.setCode(authorizationCode);
            // 得到 到客户端请求地址中的redirect_uri重定向地址
            String webServerRedirectUri = clientInfo.getWebServerRedirectUri();
            String redirectURI = StringUtil.isEmpty(webServerRedirectUri) ? oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI) : webServerRedirectUri;

            // 构建响应
            OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

            //根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity<>(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            // 处理出错
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                // 告诉客户端没有传入回调地址
                return ResultModel.newErrorModel("OAuth callback url needs to be provider by client!!");
            }

            return ResultModel.newErrorModel(e.getMessage());
        }

    }

}
