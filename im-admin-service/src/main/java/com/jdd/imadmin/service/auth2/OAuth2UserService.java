package com.jdd.imadmin.service.auth2;

import com.alibaba.fastjson.JSON;
import com.jdd.imadmin.common.redis.RedisManager;
import com.jdd.imadmin.dao.condition.BatchUserOauthInsertVO;
import com.jdd.imadmin.dao.entity.Oauth2Code;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import com.jdd.imadmin.dao.mapper.Oauth2CodeMapper;
import com.jdd.imadmin.dao.mapper.Oauth2UserTokenMapper;
import com.jdd.imadmin.service.converter.BatchInsertConverter;
import com.jdd.imadmin.service.model.BatchGenerateVO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OAuth2UserService {

    @Autowired
    private RedisManager redisManager;

    @Autowired
    Oauth2ClientService oauth2ClientService;

    @Autowired
    private Oauth2CodeMapper oauth2CodeMapper;

    @Autowired
    private Oauth2UserTokenMapper oauth2UserTokenMapper;

    @Autowired
    private BatchInsertConverter batchInsertConverter;

    private static final int OAUTH_CODE_EXPIRE_TIME = 5;

    @Transactional
    public void addAuthCode(String authCode, String userId, String clientId) {
        oauth2CodeMapper.deleteByUserAndClientId(userId, clientId);
        Oauth2Code oauth2Code = new Oauth2Code();
        oauth2Code.setClientId(clientId);
        oauth2Code.setCode(authCode);
        oauth2Code.setUserId(userId);
        oauth2Code.setCreateTime(new Date());
        oauth2Code.setExpireTime(DateUtils.addMinutes(new Date(), OAUTH_CODE_EXPIRE_TIME));
        oauth2CodeMapper.insert(oauth2Code);
    }

    public void removeAuthCode(String authCode) {
        oauth2CodeMapper.deleteByCode(authCode);
    }

    public Oauth2Code getOauthCodeInfo(String authCode) {
        return oauth2CodeMapper.selectByCode(authCode);
    }

    @Transactional
    public Oauth2UserToken addAccessToken(Oauth2UserToken token) {
        Oauth2UserToken oldToken = oauth2UserTokenMapper.selectByUserAndClientId(token.getUserId(), token.getClientId());
        if (oldToken == null) {
            //生成openId
            String openId = UUID.randomUUID().toString().replaceAll("-", "");
            token.setOpenId(openId);
            oauth2UserTokenMapper.insert(token);
        } else {
            token.setId(oldToken.getId());
            token.setOpenId(oldToken.getOpenId());
            oauth2UserTokenMapper.updateByPrimaryKey(token);
        }
        return token;
    }

    @Transactional
    public Oauth2UserToken addAccessToken(Oauth2UserToken token, String oauthCode) {
        removeAuthCode(oauthCode);
        return this.addAccessToken(token);
    }

    public String getUsernameByAccessToken(String accessToken) {
        Oauth2UserToken oauth2Token = getTokenByAccessToken(accessToken);
        return oauth2Token != null ? oauth2Token.getOpenId() : "";
    }

    public Oauth2UserToken getTokenByAccessToken(String accessToken) {
        Oauth2UserToken oauth2UserToken = null;
        String cache = redisManager.get(accessToken);
        if (StringUtil.isEmpty(cache)) {
            oauth2UserToken = oauth2UserTokenMapper.selectByAccessToken(accessToken);
            redisManager.set(accessToken, JSON.toJSONString(oauth2UserToken));
            redisManager.expire(accessToken, 60 * 60 * 1000);

        } else {
            oauth2UserToken = JSON.parseObject(cache, Oauth2UserToken.class);
        }

        if (oauth2UserToken != null && oauth2UserToken.getAccessTokenExpireTime().after(new Date())) {
            return oauth2UserToken;
        }

        return null;
    }

    public Oauth2UserToken getTokenByRefreshToken(String refreshToken) {
        Oauth2UserToken oauth2UserToken = null;
        String cache = redisManager.get(refreshToken);
        if (StringUtil.isEmpty(cache)) {
            oauth2UserToken = oauth2UserTokenMapper.selectByRefreshToken(refreshToken);
            redisManager.set(refreshToken, JSON.toJSONString(oauth2UserToken));
            redisManager.expire(refreshToken, 60 * 5 * 1000);

        } else {
            oauth2UserToken = JSON.parseObject(cache, Oauth2UserToken.class);
        }

        if (oauth2UserToken != null && oauth2UserToken.getRefreshTokenExpireTime().after(new Date())) {
            return oauth2UserToken;
        }

        return null;
    }


    public boolean isOpenExists(String userOpenId) {
        return oauth2UserTokenMapper.selectByOpenId(userOpenId) != null;
    }


    public Oauth2UserToken getUserOauthInfoByOpenId(String openId) {
        return oauth2UserTokenMapper.selectByOpenId(openId);
    }

    /**
     * 单次生成
     */
    public String generateOpenId(String userId, String clientId) {
        Oauth2UserToken oldToken = oauth2UserTokenMapper.selectByUserAndClientId(userId, clientId);
        if (oldToken == null) {
            //生成openId
            Oauth2UserToken token = new Oauth2UserToken();
            String openId = UUID.randomUUID().toString().replaceAll("-", "");
            token.setOpenId(openId);
            token.setClientId(clientId);
            token.setUserId(userId);
            oauth2UserTokenMapper.insert(token);
            return openId;
        } else {
            return oldToken.getOpenId();
        }
    }


    /**
     * 特殊逻辑，商户openid即用户用户openid
     */
    public void generateMerchantUserOpenId(String merchantOpenId, String clientId) {
        Oauth2UserToken oldToken = oauth2UserTokenMapper.selectByUserAndClientId(merchantOpenId, clientId);
        if (oldToken == null) {
            //生成openId
            Oauth2UserToken token = new Oauth2UserToken();
            String openId = merchantOpenId;
            token.setOpenId(openId);
            token.setUserId(openId);
            token.setClientId(clientId);
            oauth2UserTokenMapper.insert(token);
        }
    }

    /**
     * 批量生成，数量不能超过20
     */
    public List<BatchUserOauthInsertVO> batchGenrateOpenId(List<BatchGenerateVO> list) {
        List<BatchUserOauthInsertVO> insertList = batchInsertConverter.vos2dos(list);
        oauth2UserTokenMapper.batchInsert(insertList);
        return insertList;
    }
}
