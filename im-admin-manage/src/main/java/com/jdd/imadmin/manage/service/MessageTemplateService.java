package com.jdd.imadmin.manage.service;

import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.model.MessageTemplateModel;
import com.jdd.imadmin.manage.controller.param.MessageTemplateParam;
import com.jdd.imadmin.manage.controller.param.PageParam;

public interface MessageTemplateService {

    /**
     * 订阅信息列表
     * @param param
     * @return
     */
    PageInfo listMessageTemplate(PageParam param);

    /**
     * 新增订阅通知
     * @param param
     * @return
     */
    RestResult saveMessageTemplate(MessageTemplateModel param);

    /**
     * 修改订阅通知
     * @param param
     * @return
     */
    RestResult updateMessageTemplate(MessageTemplateModel param);

    /**
     * 删除订阅通知
     * @param param
     * @return
     */
    RestResult deleteMessageTemplate(MessageTemplateParam param);

    /**
     * 订阅通知详情
     * @param param
     * @return
     */
    RestResult messageTemplateDetail(MessageTemplateParam param);
}
