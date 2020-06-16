package com.jdd.imadmin.api.handler;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.MessagePayload;
import cn.wildfirechat.pojos.SendMessageResult;
import cn.wildfirechat.sdk.MessageAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.MessageParam;
import com.jdd.imadmin.common.enums.MessageTypeEnum;
import com.jdd.imadmin.common.enums.PushTypeEnum;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author @sailength on 2020/3/26.
 */
public abstract class ServiceAccountPushHandler {


    @Autowired
    private OAuth2UserService oAuth2UserService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ResultModel send(String serviceOpenId,String merchantOpenId, String openId, MessagePayload payload, Integer pushType, Integer messageType) {
        String userId = null;
        if (!StringUtils.isEmpty(openId) && pushType == PushTypeEnum.SINGLE.getValue()) {
            Oauth2UserToken oauth2UserToken = oAuth2UserService.getUserOauthInfoByOpenId(openId);
            if (oauth2UserToken == null) {
                return ResultModel.openIdInvalid();
            }
            userId = oauth2UserToken.getUserId();
        }


        IMResult<SendMessageResult> resultSendMessage = null;
        try {
            payload.setType(this.getType().getInnerType());
            resultSendMessage = MessageAdmin.sendChannelMessage(serviceOpenId,merchantOpenId, userId, payload);
        } catch (Exception e) {
            logger.info("【发送服务号消息】发送服务号消息异常", e);
            return ResultModel.newErrorModel(e.getMessage());
        }
        if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            return ResultModel.newSuccessModel("推送成功");
        } if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_MERCHANT_SERVICE_NOT_MATCH) {
            return ResultModel.newErrorModel(ErrorCode.ERROR_CODE_MERCHANT_SERVICE_NOT_MATCH.msg);
        }else {
            return ResultModel.parameterAbnormality();
        }
    }

    public abstract ResultModel excuteSend(MessageParam param);


    public abstract MessageTypeEnum getType();
}
