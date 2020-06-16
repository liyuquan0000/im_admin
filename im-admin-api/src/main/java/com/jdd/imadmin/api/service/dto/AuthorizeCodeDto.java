package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AuthorizeCodeDto{
    @JSONField(name = "authorize_code")
    private String code;
    @JSONField(name = "redirect_url")
    private String redirectUrl;
}
