package com.jdd.imadmin.api.service.impl;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.VersionParam;
import com.jdd.imadmin.api.service.VersionService;
import com.jdd.imadmin.api.service.dto.VersionDTO;
import com.jdd.imadmin.common.enums.ApiErrorCode;
import com.jdd.imadmin.dao.entity.ImVersion;
import com.jdd.imadmin.dao.mapper.ImVersionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private ImVersionMapper versionMapper;

    @Override
    public ResultModel getVersionDetail(VersionParam param) {
        if (param.getPlatform() == null) {
            return ResultModel.newErrorModel("平台类型不能为空 1 iOS 2 Android");
        }
        else {
            Example example = new Example(ImVersion.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("platform", param.getPlatform());
            criteria.andEqualTo("status", 1);
            example.orderBy("versionCode").desc();
            List<ImVersion> list = versionMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                ImVersion max = list.get(0);
                VersionDTO versionDTO = new VersionDTO();
                BeanUtils.copyProperties(max, versionDTO);
                return ResultModel.newSuccessModel(versionDTO);
            } else {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_NONE_VERSION);
            }
        }
    }
}
