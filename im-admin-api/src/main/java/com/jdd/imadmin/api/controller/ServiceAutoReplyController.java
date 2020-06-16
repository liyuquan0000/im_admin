package com.jdd.imadmin.api.controller;

import com.jdd.imadmin.api.controller.param.AutoReplyParam;
import com.jdd.imadmin.api.service.ServiceAutoReplyService;
import com.jdd.imadmin.dao.base.RestResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: liquan
 * @Date: 2020/4/23
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/public/serviceAutoReply")
public class ServiceAutoReplyController {

    @Autowired
    private ServiceAutoReplyService serviceAutoReplyService;

    @PostMapping(value = "/queryAutoReply")
    public RestResult queryAutoReply(@RequestBody @Valid AutoReplyParam autoReplyDto) {
        if (!StringUtils.isNotEmpty(autoReplyDto.getKeyword())&&!StringUtils.isNotEmpty(autoReplyDto.getService_open_id())) {
            return RestResult.error(RestResult.RestCode.FAILURE);
        }
        return serviceAutoReplyService.queryAutoReply(autoReplyDto);
    }


}
