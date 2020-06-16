package com.jdd.imadmin.api.controller.oauth.controller;

import cn.wildfirechat.pojos.InputOutputUserInfo;
import cn.wildfirechat.sdk.UserAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.oauth.constant.Oauth2Constants;

import com.jdd.imadmin.api.controller.oauth.vo.OauthUserInfo;
import com.jdd.imadmin.common.util.LogExceptionStackTrace;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
@RestController
@Slf4j
@RequestMapping("oauth2/")
public class UserInfoController {

    @Autowired
    private OAuth2UserService oAuth2Service;

    @RequestMapping("/userinfo")
    public Object userInfo(HttpServletRequest request) throws OAuthSystemException {
        try {
            //构建 OAuth2 资源请求
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            // 获取Access Token
            String accessToken = oauthRequest.getAccessToken();
            // 验证Access Token
            Oauth2UserToken oauth2UserToken = oAuth2Service.getTokenByAccessToken(accessToken);
            if (oauth2UserToken == null) {
                log.info("accessToken 已过期  accessToken:{}", accessToken);
                return ResultModel.accessTokenInvalid();
            }

            String openId = request.getParameter(Oauth2Constants.OAUTH_OPEN_ID);
            if (!oauth2UserToken.getOpenId().equals(openId)) {
                log.info("openid does'nt match access_token  accessToken:{}, openId:{}", accessToken, openId);
                return ResultModel.newErrorModel("open_id does'nt match access_token");
            }

            //获取用户信息
            String userId = String.valueOf(oauth2UserToken.getUserId());
            IMResult<InputOutputUserInfo> imResult = null;
            try {
                imResult = UserAdmin.getUserByUserId(userId);
            } catch (Exception e) {
                log.error("[调用IM Server获取用户信息时异常] exception:{}", LogExceptionStackTrace.erroStackTrace(e));
            }

            if (imResult == null || imResult.getResult() == null || imResult.getCode() != 0) {
                return ResultModel.newErrorModel("userinfo does'nt exist");
            }

            InputOutputUserInfo userInfo = imResult.getResult();
            OauthUserInfo oauthUserInfo = new OauthUserInfo();
            oauthUserInfo.setOpenId(openId);
            oauthUserInfo.setNickname(userInfo.getDisplayName());
            oauthUserInfo.setHeadurl(userInfo.getPortrait());
            return ResultModel.newSuccessModel(oauthUserInfo);
        } catch (OAuthProblemException e) {
            log.error("[获取用户信息时异常] exception:{}", LogExceptionStackTrace.erroStackTrace(e));
            return ResultModel.newErrorModel(e.getMessage());
        }
    }
}
