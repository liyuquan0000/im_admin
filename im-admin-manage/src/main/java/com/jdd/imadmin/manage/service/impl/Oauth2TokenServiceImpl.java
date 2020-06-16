package com.jdd.imadmin.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.entity.Oauth2Code;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.dao.mapper.Oauth2TokenMapper;
import com.jdd.imadmin.manage.controller.model.Oauth2CodeModel;
import com.jdd.imadmin.manage.controller.model.Oauth2TokenModel;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.Oauth2TokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Oauth2TokenServiceImpl implements Oauth2TokenService {

    @Autowired
    private Oauth2TokenMapper oauth2TokenMapper;

    public static List<Oauth2TokenModel> convert(List<Oauth2Token> source, List<Oauth2TokenModel> target) {
        for (Oauth2Token code : source) {
            Oauth2TokenModel model = new Oauth2TokenModel();
            BeanUtils.copyProperties(code, model);
            target.add(model);
        }
        return target;
    }

    @Override
    public PageInfo listOauth2Token(PageParam param) {
        PageHelper.startPage(param.getPageNum(),param.getPageSize());

        List<Oauth2Token> list = oauth2TokenMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
