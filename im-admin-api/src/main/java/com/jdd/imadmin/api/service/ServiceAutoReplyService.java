package com.jdd.imadmin.api.service;

import com.jdd.imadmin.api.controller.param.AutoReplyParam;
import com.jdd.imadmin.dao.base.RestResult;

/**
 * @Author: liquan
 * @Date: 2020/4/23
 * @Version 1.0
 */
public interface ServiceAutoReplyService {

    RestResult queryAutoReply(AutoReplyParam autoReplyDto);

}
