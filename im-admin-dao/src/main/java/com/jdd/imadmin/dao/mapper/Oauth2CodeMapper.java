package com.jdd.imadmin.dao.mapper;

import com.jdd.imadmin.dao.base.BaseMapper;
import com.jdd.imadmin.dao.entity.Oauth2Code;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2CodeMapper extends BaseMapper<Oauth2Code> {

    void deleteByCode(@Param("code") String code);

    Oauth2Code selectByCode(@Param("code") String code);

    void deleteByUserAndClientId(@Param("userId") String userId, @Param("clientId") String clientId);
}