package com.jdd.imadmin.api.controller.oauth.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2019-08-01 19:21
 ************************************************************/

@Data
public class OauthUserInfo {

    @JSONField(name = "open_id")
    private String openId;

    @JSONField(name = "nickname")
    private String nickname;

    @JSONField(name = "headurl")
    private String headurl;
}
