package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class TokenDto implements Serializable {
    @JSONField(name = "token")
    private String token;
    @JSONField(name = "expire_time")
    private String expireTime;
}
