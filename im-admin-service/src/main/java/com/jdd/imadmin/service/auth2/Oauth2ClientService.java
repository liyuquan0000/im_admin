package com.jdd.imadmin.service.auth2;

import com.jdd.imadmin.common.redis.RedisManager;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.dao.mapper.Oauth2ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Oauth2ClientService {

    @Autowired
    private Oauth2ClientMapper oauth2ClientMapper;

    @Autowired
    private RedisManager redisManager;

    public Oauth2Client findByClientId(String clientId) {
        return oauth2ClientMapper.selectByClientId(clientId);
    }

    public Oauth2Client getClientInfo(String clientId, String clientSecret) {
        return oauth2ClientMapper.getClientInfo(clientId, clientSecret);
    }
}
