package com.jdd.imadmin.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.entity.Oauth2Code;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import com.jdd.imadmin.dao.mapper.Oauth2UserTokenMapper;
import com.jdd.imadmin.manage.controller.model.Oauth2CodeModel;
import com.jdd.imadmin.manage.controller.model.Oauth2UserTokenModel;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.Oauth2UserTokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Oauth2UserTokenServiceImpl implements Oauth2UserTokenService {

    @Autowired
    private Oauth2UserTokenMapper  oauth2UserTokenMapper;

    public static List<Oauth2UserTokenModel> convert(List<Oauth2UserToken> source, List<Oauth2UserTokenModel> target) {
        for (Oauth2UserToken code : source) {
            Oauth2UserTokenModel model = new Oauth2UserTokenModel();
            BeanUtils.copyProperties(code, model);
            target.add(model);
        }
        return target;
    }

    @Override
    public PageInfo listOauth2UserToken(PageParam param) {
        PageHelper.startPage(param.getPageNum(),param.getPageSize());

        List<Oauth2UserToken> list = oauth2UserTokenMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
