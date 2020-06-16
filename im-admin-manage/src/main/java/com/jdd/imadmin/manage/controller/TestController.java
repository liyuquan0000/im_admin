package com.jdd.imadmin.manage.controller;

import com.jdd.imadmin.manage.web.ClientRequestParam;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"服务接口"})
@RestController
@RequestMapping(value = "/public/TestController")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @ApiOperation(value = "获取版本")
    @PostMapping(value = "/getStrategy")
    public String getStrategy(@RequestBody ClientRequestParam<String> clientRequestParam) {
        logger.info("哈哈哈哈");
        clientRequestParam.getHeader();
       return "";
    }
}
