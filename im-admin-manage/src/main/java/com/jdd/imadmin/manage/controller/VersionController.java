package com.jdd.imadmin.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.model.VersionModel;
import com.jdd.imadmin.manage.controller.param.VersionParam;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.VersionService;
import com.jdd.imadmin.manage.service.impl.VersionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"App版本"})
@RestController
@RequestMapping(value = "/version", produces = "application/json;charset=UTF-8")
public class VersionController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private VersionService versionService;

    @ApiOperation(value = "App版本列表", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = VersionModel.class)})
    @RequestMapping("listVersion")
    public RestResult listVersion(@RequestBody PageParam param, HttpServletRequest request) {
        try {
            PageInfo pageInfo = versionService.listVersion(param);
            List<VersionModel> list = new ArrayList<>();
            list = VersionServiceImpl.convert(pageInfo.getList(), list);
            pageInfo.setList(list);
            return RestResult.ok(pageInfo);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "新增版本", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @RequestMapping("saveVersion")
    public RestResult saveVersion(@RequestBody VersionModel param, HttpServletRequest request) {
        try{
            return versionService.saveVersion(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "修改版本信息", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @RequestMapping("updateVersion")
    public RestResult updateVersion(@RequestBody VersionModel param, HttpServletRequest request) {
        try{
            return versionService.updateVersion(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "查询版本信息", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = VersionModel.class)})
    @RequestMapping("versionDetail")
    public RestResult versionDetail(@RequestBody VersionParam param, HttpServletRequest request) {
        try{
            return versionService.versionDetail(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "修改版本状态", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @RequestMapping("updateVersionStatus")
    public RestResult updateVersionStatus(@RequestBody VersionParam param, HttpServletRequest request) {
        try{
            return versionService.updateVersionStatus(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }

    @ApiOperation(value = "删除版本信息", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = RestResult.class)})
    @RequestMapping("deleteVersion")
    public RestResult deleteVersion(@RequestBody VersionParam param, HttpServletRequest request) {
        try{
            return versionService.deleteVersion(param);
        } catch (Exception e) {
            logger.error("异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
            return RestResult.failure(null);
        }
    }
}
