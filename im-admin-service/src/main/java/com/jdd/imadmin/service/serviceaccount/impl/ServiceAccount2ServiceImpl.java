package com.jdd.imadmin.service.serviceaccount.impl;


import com.jdd.imadmin.dao.entity.ImAutoReply;
import com.jdd.imadmin.dao.mapper.ImAutoReplyMapper;
import com.jdd.imadmin.service.serviceaccount.ServiceAccount2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author @sailength on 2020/2/20.
 *         服务号对外门户类
 */
@Service
public class ServiceAccount2ServiceImpl implements ServiceAccount2Service, BeanPostProcessor {


    @Autowired
    private ImAutoReplyMapper imAutoReplyMapper;

    /**
     * 查询自动回复
     */
    @Override
    public ImAutoReply getAnyAutoReply(String serviceOpenId, String keyword) {
        Assert.notNull("serviceOpenId cant be null", serviceOpenId);
        Assert.notNull("keyword cant be null", serviceOpenId);
        List<ImAutoReply> list = imAutoReplyMapper.queryAutoReplyByKeyWord(keyword, serviceOpenId);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
