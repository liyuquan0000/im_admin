package com.jdd.imadmin.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.model.MessageTemplateModel;
import com.jdd.imadmin.manage.controller.param.MessageTemplateParam;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.MessageTemplateService;
import com.jdd.imadmin.manage.service.impl.MessageTemplateServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "订阅消息管理")
@RestController
@RequestMapping(value = "/messageTemplate", produces = "application/json;charset=UTF-8")
public class MessageTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MessageTemplateService messageTemplateService;

    @ApiOperation(value = "订阅通知列表", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = MessageTemplateModel.class)})
    @PostMapping(value = "/listMessageTemplate")
    public RestResult listMessageTemplate(@RequestBody PageParam param, HttpServletRequest request) {
        try{
            PageInfo pageInfo = messageTemplateService.listMessageTemplate(param);
            return RestResult.ok(pageInfo);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "新增订阅通知", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/saveMessageTemplate")
    public RestResult saveMessageTemplate(@RequestBody MessageTemplateModel param, HttpServletRequest request) {
        try{
            return messageTemplateService.saveMessageTemplate(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "修改订阅通知", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/updateMessageTemplate")
    public RestResult updateMessageTemplate(@RequestBody MessageTemplateModel param, HttpServletRequest request) {
        try{
            return messageTemplateService.updateMessageTemplate(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "删除订阅通知", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/deleteMessageTemplate")
    public RestResult deleteMessageTemplate(@RequestBody MessageTemplateParam param, HttpServletRequest request) {
        try{
            return messageTemplateService.deleteMessageTemplate(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "查询订阅通知", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = MessageTemplateModel.class)})
    @PostMapping(value = "/messageTemplateDetail")
    public RestResult messageTemplateDetail(@RequestBody MessageTemplateParam param, HttpServletRequest request) {
        try{
            return messageTemplateService.messageTemplateDetail(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }


}
