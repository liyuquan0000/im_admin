package com.jdd.imadmin.service;

import com.jdd.imadmin.api.Oauth2ClinetConfigService;
import com.jdd.imadmin.api.model.BaseRpcDTO;
import com.jdd.imadmin.api.model.Oauth2ClientDTO;
import com.jdd.imadmin.common.redis.RedisManager;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.dao.mapper.Oauth2ClientMapper;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.jdd.imadmin.service.auth2.OAuth2UserService;

/**
 * @author @sailength on 2020/3/6.
 */
@Service
public class Oauth2ClinetConfigServiceImpl implements Oauth2ClinetConfigService {
    private static final Logger logger = LoggerFactory.getLogger(RedisManager.class);
    @Autowired
    private Oauth2ClientMapper oauth2ClientMapper;

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Override
    public BaseRpcDTO<Oauth2ClientDTO> getAuth2Config(String clientId) {
        Oauth2Client oauth2Client = oauth2ClientMapper.selectByClientId(clientId);
        Oauth2ClientDTO oauth2ClientDTO = new Oauth2ClientDTO();
        BeanUtils.copyProperties(oauth2Client, oauth2ClientDTO);
        return BaseRpcDTO.newSuccessModel(oauth2ClientDTO);
    }


    @Override
    public BaseRpcDTO<String> getOpenId(String clientId, String userId) {
        String openId = oAuth2UserService.generateOpenId(userId, clientId);
        return BaseRpcDTO.newSuccessModel(openId);
    }

}
