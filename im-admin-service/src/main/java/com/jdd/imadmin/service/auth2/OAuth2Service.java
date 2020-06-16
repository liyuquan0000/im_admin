package com.jdd.imadmin.service.auth2;

import com.alibaba.fastjson.JSON;
import com.jdd.imadmin.common.redis.RedisManager;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.dao.mapper.Oauth2TokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;

@Service
public class OAuth2Service {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisManager redisManager;

    @Autowired
    Oauth2ClientService oauth2ClientService;

    @Autowired
    private Oauth2TokenMapper oauth2TokenMapper;


    @Transactional
    public void addAccessToken(Oauth2Token token) {
        Oauth2Token oldToken = oauth2TokenMapper.selectByClientId(token.getClientId());
        if (oldToken == null) {
            oauth2TokenMapper.insert(token);
        } else {
            token.setId(oldToken.getId());
            oauth2TokenMapper.updateByPrimaryKey(token);
        }
    }

    public Oauth2Token getTokenByAccessToken(String accessToken) {


        Oauth2Token oauth2Token = null;
        String cache = redisManager.get(accessToken);
        if (StringUtil.isEmpty(cache)) {
            oauth2Token = oauth2TokenMapper.selectByAccessToken(accessToken);
            redisManager.set(accessToken, JSON.toJSONString(oauth2Token));
            redisManager.expire(accessToken, 60 * 60 * 1000);

        } else {
            oauth2Token = JSON.parseObject(cache, Oauth2Token.class);
        }

        if (oauth2Token != null && oauth2Token.getAccessTokenExpireTime().after(new Date())) {
            return oauth2Token;
        }
        logger.info("token {} 已过期", accessToken);

        return null;
    }

}
