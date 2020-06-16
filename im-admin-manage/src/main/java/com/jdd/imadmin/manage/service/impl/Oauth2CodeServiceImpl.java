package com.jdd.imadmin.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.entity.Oauth2Code;
import com.jdd.imadmin.dao.mapper.Oauth2CodeMapper;
import com.jdd.imadmin.manage.controller.model.Oauth2CodeModel;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.Oauth2CodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Oauth2CodeServiceImpl implements Oauth2CodeService {

    @Autowired
    private Oauth2CodeMapper oauth2CodeMapper;

    public static List<Oauth2CodeModel> convert(List<Oauth2Code> source, List<Oauth2CodeModel> target) {
        for (Oauth2Code code : source) {
            Oauth2CodeModel model = new Oauth2CodeModel();
            BeanUtils.copyProperties(code, model);
            target.add(model);
        }
        return target;
    }

    @Override
    public PageInfo listOauth2Code(PageParam param) {
        PageHelper.startPage(param.getPageNum(),param.getPageSize());

        List<Oauth2Code> list = oauth2CodeMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
