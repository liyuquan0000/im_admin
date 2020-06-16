package com.jdd.imadmin.api.handler.impl;

import cn.wildfirechat.pojos.MessagePayload;
import com.alibaba.fastjson.JSON;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.MessageParam;
import com.jdd.imadmin.api.handler.ServiceAccountPushHandler;
import com.jdd.imadmin.api.handler.model.TemplateContent;
import com.jdd.imadmin.api.handler.model.TemplateMessageVO;
import com.jdd.imadmin.common.enums.ApiErrorCode;
import com.jdd.imadmin.common.enums.HrefTypeEnum;
import com.jdd.imadmin.common.enums.MessageTypeEnum;
import com.jdd.imadmin.common.enums.PushTypeEnum;
import com.jdd.imadmin.dao.entity.ImMessageTemplate;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.dao.mapper.ImMessageTemplateMapper;
import com.jdd.imadmin.dao.mapper.Oauth2ClientMapper;
import com.jdd.imadmin.dao.mapper.Oauth2TokenMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author @sailength on 2020/3/26.
 */
@Component
public class PicTextPushHandler extends ServiceAccountPushHandler {


    @Autowired
    private ImMessageTemplateMapper messageTemplateMapper;

    @Autowired
    private Oauth2ClientMapper oauth2ClientMapper;

    @Autowired
    private Oauth2TokenMapper oauth2TokenMapper;

    @Override
    public ResultModel excuteSend(MessageParam param) {

        if (StringUtils.isBlank(param.getToUserId()) && param.getPushType() == PushTypeEnum.SINGLE.getValue()) {
            return ResultModel.newErrorModel("推送对象为空");
        }

        Example tokenExample = new Example(Oauth2Token.class);
        tokenExample.createCriteria().andEqualTo("accessToken", param.getToken());
        List<Oauth2Token> oauth2Tokens = oauth2TokenMapper.selectByExample(tokenExample);
        if (oauth2Tokens == null || oauth2Tokens.isEmpty()) {
            return ResultModel.accessTokenInvalid();
        }
        String clientId = oauth2Tokens.get(0).getClientId();

        TemplateContent templateContent = JSON.parseObject(param.getContent(), TemplateContent.class);
        Example example = new Example(ImMessageTemplate.class);
        example.createCriteria().andEqualTo("templateCode", templateContent.getTemplateCode());
        List<ImMessageTemplate> messageTemplates = messageTemplateMapper.selectByExample(example);
        if (messageTemplates != null && !messageTemplates.isEmpty()) {
            ImMessageTemplate template = messageTemplates.get(0);
            String clientNumber = template.getClientNumber();

            Example example2 = new Example(Oauth2Client.class);
            example2.createCriteria().andEqualTo("clientId", clientId);
            List<Oauth2Client> oauth2Clients = oauth2ClientMapper.selectByExample(example2);
            if (oauth2Clients != null && !oauth2Clients.isEmpty()) {
                if (!(clientNumber.equals(oauth2Clients.get(0).getClientNumber())))
                    return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_TEMPLATE_NOT_MATCH);
            } else {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_CLIENT_NOT_FOUND);
            }

            String content = replacePlaceholder(messageTemplates.get(0).getContent(), templateContent.getTemlateParams());
            TemplateMessageVO templateMessageVO = new TemplateMessageVO();
            templateMessageVO.setUrl(messageTemplates.get(0).getUrl());
            templateMessageVO.setContent(content);
            templateMessageVO.setPic(templateContent.getPic());
            templateMessageVO.setSubject(templateContent.getSubject());
            templateMessageVO.setType(HrefTypeEnum.HREF.getValue());
            MessagePayload messagePayload = new MessagePayload();
            messagePayload.setSearchableContent(JSON.toJSONString(templateMessageVO));
            return this.send(param.getServiceOpenId(), param.getMerchantOpenId(),param.getToUserId(), messagePayload, param.getPushType(), param.getMessageType());
//            return ResultModel.newSuccessModel("发送成功");
        }
        return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_TEMPLATE_NOT_FOUND);
    }

    public String replacePlaceholder(String template, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                template = template.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
        }
        return template;
    }

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.TEMPLATE;
    }
}
