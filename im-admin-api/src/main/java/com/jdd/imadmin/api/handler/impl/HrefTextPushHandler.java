package com.jdd.imadmin.api.handler.impl;


import cn.wildfirechat.pojos.MessagePayload;
import com.alibaba.fastjson.JSON;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.MessageParam;
import com.jdd.imadmin.api.handler.ServiceAccountPushHandler;
import com.jdd.imadmin.api.handler.model.HrefContent;
import com.jdd.imadmin.api.handler.model.HrefMessageVO;
import com.jdd.imadmin.common.enums.HrefTypeEnum;
import com.jdd.imadmin.common.enums.MessageTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @author @sailength on 2020/3/26.
 */
@Component
public class HrefTextPushHandler extends ServiceAccountPushHandler {


    @Override
    public ResultModel excuteSend(MessageParam param) {
        String content = param.getContent();
        HrefContent hrefContent = JSON.parseObject(content, HrefContent.class);
        String url = hrefContent.getUrl();
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.setSearchableContent(JSON.toJSONString(new HrefMessageVO(url, hrefContent.getContent(), HrefTypeEnum.HREF.getValue())));
        return this.send(param.getServiceOpenId(), param.getMerchantOpenId(),param.getToUserId(), messagePayload,param.getPushType(),param.getMessageType());
    }

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.HREF_TEXT;
    }
}
