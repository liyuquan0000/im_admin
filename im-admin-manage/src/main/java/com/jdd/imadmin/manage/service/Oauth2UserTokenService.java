package com.jdd.imadmin.manage.service;

import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.manage.controller.param.PageParam;

public interface Oauth2UserTokenService {

    /**
     * 列表
     * @param param
     * @return
     */
    PageInfo listOauth2UserToken(PageParam param);
}
