package com.jdd.imadmin.api.service.impl;

import com.jdd.imadmin.api.controller.param.AutoReplyParam;
import com.jdd.imadmin.api.converter.AutoReplyConverter;
import com.jdd.imadmin.api.service.ServiceAutoReplyService;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.dao.entity.ImAutoReply;
import com.jdd.imadmin.dao.mapper.ServiceAutoReplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @Author: liquan
 * @Date: 2020/4/23
 * @Version 1.0
 */
@Service
public class ServiceAutoReplyServiceImpl implements ServiceAutoReplyService, BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServiceAutoReplyMapper serviceAutoReplyMapper;

    @Autowired
    private AutoReplyConverter autoReplyConverter;


    /**
     * 查询某个服务号的自动回复设置
     */
    @Override
    public RestResult queryAutoReply(AutoReplyParam autoReplyDto) {
        String seriviceOpenId = autoReplyDto.getService_open_id();
        String keyword = autoReplyDto.getKeyword();
        Integer contentType = autoReplyDto.getContent_type();
        List<ImAutoReply> list = serviceAutoReplyMapper.queryAutoReply(seriviceOpenId,keyword,contentType);
        if (list.size() > 0) {
            ImAutoReply imAutoReply = list.get(0);
            if (imAutoReply.getContent() == null) {
                return RestResult.ok(null);
            } else {
                return RestResult.ok(imAutoReply.getContent());
            }
        } else{
            return RestResult.failure("没有查询到数据");
        }
    }
}
