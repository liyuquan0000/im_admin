package com.jdd.imadmin.manage.controller;

import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.model.Oauth2UserTokenModel;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.Oauth2UserTokenService;
import com.jdd.imadmin.manage.service.impl.Oauth2UserTokenServiceImpl;
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

@Api(tags = {"用户token"})
@RestController
@RequestMapping(value = "/oauth2UserToken", produces = "application/json;charset=UTF-8")
public class Oauth2UserTokenController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private Oauth2UserTokenService oauth2UserTokenService;

    @ApiOperation(value = "用户token列表", httpMethod = "POST", notes = "孙莘莘")
    @ApiResponses({@ApiResponse(code = 0, message = "data", response = Oauth2UserTokenModel.class)})
    @PostMapping(value = "/listOauth2UserToken")
    public RestResult listOauth2UserToken(@RequestBody PageParam param, HttpServletRequest request) {
        try {
            PageInfo pageInfo = oauth2UserTokenService.listOauth2UserToken(param);
            List<Oauth2UserTokenModel> list = new ArrayList<>();
            list = Oauth2UserTokenServiceImpl.convert(pageInfo.getList(), list);
            pageInfo.setList(list);
            return RestResult.ok(pageInfo);
        } catch (Exception e) {
            return RestResult.failure(null);
        }
    }
}
