package com.jdd.imadmin.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.dao.entity.ImVersion;
import com.jdd.imadmin.dao.mapper.ImVersionMapper;
import com.jdd.imadmin.manage.controller.model.VersionModel;
import com.jdd.imadmin.manage.controller.param.VersionParam;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.service.VersionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private ImVersionMapper imVersionMapper;

    public static List<VersionModel> convert(List<ImVersion> source, List<VersionModel> target) {
        for (ImVersion version : source) {
            VersionModel model = new VersionModel();
            BeanUtils.copyProperties(version, model);
            target.add(model);
        }
        return target;
    }

    @Override
    public PageInfo listVersion(PageParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<ImVersion> list = imVersionMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public RestResult saveVersion(VersionModel param) {
        Example example = new Example(ImVersion.class);
        example.createCriteria().andEqualTo("versionCode", param.getVersionCode());
        List<ImVersion> imVersions = imVersionMapper.selectByExample(example);
        if (imVersions.size() > 0 ) {
            return RestResult.failure("版本号已存在");
        }

        ImVersion version = new ImVersion();
        BeanUtils.copyProperties(param, version);
        version.setModifyTime(new Date());
        imVersionMapper.insert(version);
        return RestResult.ok("添加成功");
    }

    @Override
    public RestResult updateVersion(VersionModel param) {
        ImVersion version = new ImVersion();
        BeanUtils.copyProperties(param, version);
        version.setModifyTime(new Date());
        imVersionMapper.updateByPrimaryKeySelective(version);
        return RestResult.ok("更新成功");
    }

    @Override
    public RestResult versionDetail(VersionParam param) {
        Long id = param.getId();
        ImVersion version = imVersionMapper.selectByPrimaryKey(id);
        if (version == null) {
            return RestResult.failure("没有查询到数据");
        }
        VersionModel model = new VersionModel();
        BeanUtils.copyProperties(version, model);
        return RestResult.ok(model);
    }

    @Override
    public RestResult updateVersionStatus(VersionParam param) {
        Long id = param.getId();
        ImVersion version = imVersionMapper.selectByPrimaryKey(id);
        int flag = version.getStatus();
        if (flag == 1) {
            version.setStatus(0);
        }
        else {
            version.setStatus(1);
        }
        version.setModifyTime(new Date());
        imVersionMapper.updateByPrimaryKeySelective(version);
        return RestResult.ok("更新成功");
    }

    @Override
    public RestResult deleteVersion(VersionParam param) {
        Long id = param.getId();
        imVersionMapper.deleteByPrimaryKey(id);
        return RestResult.ok("删除成功");
    }
}
