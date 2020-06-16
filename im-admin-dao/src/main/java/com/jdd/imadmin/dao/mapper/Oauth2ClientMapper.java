package com.jdd.imadmin.dao.mapper;

import com.jdd.imadmin.dao.base.BaseMapper;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Oauth2ClientMapper extends BaseMapper<Oauth2Client> {

    Oauth2Client selectByClientId(@Param("clientId") String clientId);

    Oauth2Client getClientInfo(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret);

    List<Oauth2Client> getOauth2ClientList(@Param("clientId") String clientId);

    String selectMaxCode();
}