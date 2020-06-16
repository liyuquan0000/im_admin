package com.jdd.imadmin.manage.controller;

import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.model.Oauth2TokenModel;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.Oauth2TokenService;
import com.jdd.imadmin.manage.service.impl.Oauth2TokenServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(tags = {"全局token"})
@RestController
@RequestMapping(value = "/oauth2Token", produces = "application/json;charset=UTF-8")
public class Oauth2TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private Oauth2TokenService oauth2TokenService;

    @ApiOperation(value = "全局token列表", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = Oauth2TokenModel.class)})
    @PostMapping(value = "/listOauth2Token")
    public RestResult listOauth2Token(@RequestBody PageParam param, HttpServletRequest request) {
        try {
            PageInfo pageInfo = oauth2TokenService.listOauth2Token(param);
            List<Oauth2TokenModel> list = new ArrayList<>();
            list = Oauth2TokenServiceImpl.convert(pageInfo.getList(), list);
            pageInfo.setList(list);
            return RestResult.ok(pageInfo);
        } catch (Exception e) {
            return RestResult.failure(null);
        }
    }
}
