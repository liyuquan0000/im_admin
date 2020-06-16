package com.jdd.imadmin.api.service;


import com.jdd.imadmin.dao.entity.Oauth2Token;

public interface TokenVerifyService {

    boolean verify(String accessToken);

    Oauth2Token getTokenDetail(String accessToken);
}
