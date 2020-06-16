package com.jdd.imadmin.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.model.oauth2Client.ClientDownModel;
import com.jdd.imadmin.manage.controller.model.oauth2Client.ListOauth2Client;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.controller.param.oauth2Client.*;
import com.jdd.imadmin.manage.service.Oauth2ClientService;
import com.jdd.imadmin.manage.service.impl.Oauth2ClientServiceImpl;
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

@Api(tags = "技术提供商")
@RestController
@RequestMapping(value = "/oauth2Client", produces = "application/json;charset=UTF-8")
public class Oauth2ClientController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private Oauth2ClientService oauth2ClientService;


    @ApiOperation(value = "技术提供商列表", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = ListOauth2Client.class)})
    @PostMapping(value = "/listOauth2Client")
    public RestResult listOauth2Client(@RequestBody PageParam param, HttpServletRequest request) {
        try {
            PageInfo pageInfo = oauth2ClientService.listOauth2Client(param);
            List<ListOauth2Client> list = new ArrayList<>();
            list = Oauth2ClientServiceImpl.convert(pageInfo.getList(), list);
            pageInfo.setList(list);
            return RestResult.ok(pageInfo);
        } catch (Exception e) {
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "创建技术提供商", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/saveOauth2Client")
    public RestResult saveOauth2Client(@RequestBody SaveImTechnologyProvider param, HttpServletRequest request) {
        try{
            return oauth2ClientService.saveOauth2Client(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "修改通知地址", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/updateWebServerRedirectUri")
    public RestResult updateWebServerRedirectUri(@RequestBody UpdateWebServerRedirectUri param, HttpServletRequest request) {
        try{
            return oauth2ClientService.updateWebServerRedirectUri(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }


    @ApiOperation(value = "IP白名单", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = UpdateIpWhiteList.class)})
    @PostMapping(value = "/ipWhiteListDetail")
    public RestResult ipWhiteListDetail(@RequestBody Oauth2ClientParam param, HttpServletRequest request) {
        try{
            return oauth2ClientService.ipWhiteListDetail(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "修改IP信息", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/updateIpWhiteList")
    public RestResult updateIpWhiteList(@RequestBody UpdateIpWhiteList param, HttpServletRequest request) {
        try{
            return oauth2ClientService.updateIpWhiteList(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "技术提供商下拉列表", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = ClientDownModel.class)})
    @PostMapping(value = "/clientDown")
    public RestResult clientDown() {
        try{
            return oauth2ClientService.clientDown();
        } catch (Exception e) {
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "修改提供商信息", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/updateOauth2Client")
    public RestResult updateOauth2Client(@RequestBody UpdateOauth2Client param, HttpServletRequest request) {
        try{
            return oauth2ClientService.updateOauth2Client(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "删除提供商", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @PostMapping(value = "/deleteOauth2Client")
    public RestResult deleteOauth2Client(@RequestBody Oauth2ClientParam param, HttpServletRequest request) {
        try{
            return oauth2ClientService.deleteOauth2Client(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "提供商详情", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = UpdateOauth2Client.class)})
    @PostMapping(value = "/oauth2ClientDetail")
    public RestResult oauth2ClientDetail(@RequestBody Oauth2ClientParam param, HttpServletRequest request) {
        try{
            return oauth2ClientService.oauth2ClientDetail(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }


}
