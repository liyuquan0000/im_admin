package com.jdd.imadmin.dao.mapper;

import com.jdd.imadmin.dao.base.BaseMapper;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Oauth2TokenMapper extends BaseMapper<Oauth2Token> {

    Oauth2Token selectByAccessToken(@Param("accessToken") String accessToken);

    List<Oauth2Token> getOauth2TokenList(@Param("clientId") String clientId);

    Oauth2Token selectByClientId(@Param("clientId") String clientId);
}