package com.jdd.imadmin.service;

import com.alibaba.fastjson.JSON;
import com.jdd.imadmin.api.AutoReplyService;
import com.jdd.imadmin.api.model.AutoReplyDTO;
import com.jdd.imadmin.api.model.BaseRpcDTO;
import com.jdd.imadmin.converter.AutoReplyConverter;
import com.jdd.imadmin.dao.entity.ImAutoReply;
import com.jdd.imadmin.service.serviceaccount.ServiceAccount2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author @sailength on 2020/4/29.
 */
@Service
public class AutoReplyServiceImpl implements AutoReplyService {
    @Autowired
    private ServiceAccount2Service serviceAccountService;

    @Autowired
    private AutoReplyConverter autoReplyConverter;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseRpcDTO<AutoReplyDTO> queryAutoReply(String serviceOpenId, String keyword) {

        ImAutoReply imAutoReply = serviceAccountService.getAnyAutoReply(serviceOpenId, keyword);
        logger.info("[查询自动回复]查询自动回复，入参为 openid={},keyword={},返回={}", serviceOpenId, keyword,
                imAutoReply == null ? "" : JSON.toJSONString(imAutoReply));
        return BaseRpcDTO.newSuccessModel(autoReplyConverter.do2dto(imAutoReply));
    }
}
