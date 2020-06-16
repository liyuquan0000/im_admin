package com.jdd.imadmin.manage.service;

import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.controller.param.oauth2Client.*;

public interface Oauth2ClientService {

    /**
     * 技术提供商列表
     * @param param
     * @return
     */
    PageInfo listOauth2Client(PageParam param);

    /**
     * 创建技术提供商
     * @param param
     * @return
     */
    RestResult saveOauth2Client(SaveImTechnologyProvider param);

    /**
     * 修改通知地址
     * @param param
     * @return
     */
    RestResult updateWebServerRedirectUri(UpdateWebServerRedirectUri param);

    /**
     * IP白名单
     * @param param
     * @return
     */
    RestResult ipWhiteListDetail(Oauth2ClientParam param);

    /**
     * 修改IP白名单
     * @param param
     * @return
     */
    RestResult updateIpWhiteList(UpdateIpWhiteList param);

    /**
     * 技术提供商下拉
     * @return
     */
    RestResult clientDown();

    /**
     * 修改提供商信息
     * @param param
     * @return
     */
    RestResult updateOauth2Client(UpdateOauth2Client param);

    /**
     * 删除提供商
     * @param param
     * @return
     */
    RestResult deleteOauth2Client(Oauth2ClientParam param);

    /**
     * 提供商详情
     * @param param
     * @return
     */
    RestResult oauth2ClientDetail(Oauth2ClientParam param);

}
