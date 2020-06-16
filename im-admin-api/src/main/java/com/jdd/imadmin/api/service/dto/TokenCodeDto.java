package com.jdd.imadmin.api.service.dto;

import lombok.Data;

@Data
public class TokenCodeDto {

    private String token;
    private String expireTime;
    private String openId;
}
