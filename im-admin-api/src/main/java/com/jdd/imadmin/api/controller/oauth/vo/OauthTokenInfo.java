package com.jdd.imadmin.api.controller.oauth.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2019-08-01 19:21
 ************************************************************/

@Data
public class OauthTokenInfo {

    @JSONField(name = "token")
    private String token;

    @JSONField(name = "token_expire_in")
    private Integer tokenExpireIn;
}
