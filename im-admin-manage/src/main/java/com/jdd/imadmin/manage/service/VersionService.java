package com.jdd.imadmin.manage.service;

import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.manage.controller.model.VersionModel;
import com.jdd.imadmin.manage.controller.param.VersionParam;
import com.jdd.imadmin.manage.controller.param.PageParam;

public interface VersionService {

    /**
     * App版本列表
     * @param param
     * @return
     */
    PageInfo listVersion(PageParam param);

    /**
     * 新增版本
     * @param param
     * @return
     */
    RestResult saveVersion(VersionModel param);

    /**
     * 修改版本信息
     * @param param
     * @return
     */
    RestResult updateVersion(VersionModel param);

    /**
     * 查询版本信息
     * @param param
     * @return
     */
    RestResult versionDetail(VersionParam param);

    /**
     * 修改版本状态
     * @param param
     * @return
     */
    RestResult updateVersionStatus(VersionParam param);

    /**
     * 删除版本信息
     * @param param
     * @return
     */
    RestResult deleteVersion(VersionParam param);
}
