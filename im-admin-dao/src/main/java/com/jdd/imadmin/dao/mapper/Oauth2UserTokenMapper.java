package com.jdd.imadmin.dao.mapper;

import com.jdd.imadmin.dao.base.BaseMapper;
import com.jdd.imadmin.dao.condition.BatchUserOauthInsertVO;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Oauth2UserTokenMapper extends BaseMapper<Oauth2UserToken> {

    Oauth2UserToken selectByAccessToken(@Param("accessToken") String accessToken);

    Oauth2UserToken selectByRefreshToken(@Param("refreshToken") String refreshToken);

    Oauth2UserToken selectByUserAndClientId(@Param("userId") String userId, @Param("clientId") String clientId);

    Oauth2UserToken selectByOpenId(@Param("openId")String openId);

    List<Oauth2UserToken> getOauth2UserTokenList(@Param("openId") String openId, @Param("clientId") String clientId);

    /** 批量插入*/
    void batchInsert(@Param("list") List<BatchUserOauthInsertVO> list);

}