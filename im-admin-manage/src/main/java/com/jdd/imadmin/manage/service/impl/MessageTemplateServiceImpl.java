package com.jdd.imadmin.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.dao.entity.ImMessageTemplate;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.dao.mapper.ImMessageTemplateMapper;
import com.jdd.imadmin.dao.mapper.Oauth2ClientMapper;
import com.jdd.imadmin.manage.controller.model.MessageTemplateModel;
import com.jdd.imadmin.manage.controller.param.MessageTemplateParam;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.controller.param.oauth2Client.Oauth2ClientParam;
import com.jdd.imadmin.manage.service.MessageTemplateService;
import com.jdd.imadmin.manage.service.Oauth2ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Autowired
    private ImMessageTemplateMapper imMessageTemplateMapper;

    @Autowired
    private Oauth2ClientMapper oauth2ClientMapper;

    private String getName(String number) {
        Example example = new Example(Oauth2Client.class);
        example.createCriteria().andEqualTo("clientNumber", number);
        List<Oauth2Client> list = oauth2ClientMapper.selectByExample(example);
        if (list.size() > 0)
            return list.get(0).getClientName();
        else
            return null;
    }

    private List<MessageTemplateModel> convert(List<ImMessageTemplate> source, List<MessageTemplateModel> target) {
        for (ImMessageTemplate template : source) {
            MessageTemplateModel model = new MessageTemplateModel();
            BeanUtils.copyProperties(template, model);
            model.setClientName(getName(model.getClientNumber()));
            target.add(model);
        }
        return target;
    }

    @Override
    public PageInfo listMessageTemplate(PageParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<ImMessageTemplate> list = imMessageTemplateMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        List<MessageTemplateModel> modelList = new ArrayList<>();
        modelList = convert(list,modelList);
        pageInfo.setList(modelList);
        return pageInfo;
    }

    private String generateCode() {
        String code = "M000001";
        String maxCode = imMessageTemplateMapper.selectMaxCode();
        if (maxCode != null) {
            String codeStr = maxCode.substring(1);
            Integer codeNum = Integer.valueOf(codeStr) + 1;
            codeStr = String.valueOf(codeNum);
            int length = codeStr.length();
            String zeroStr = "";
            for (int i = length; i < 6; i++) {
                zeroStr = zeroStr + "0";
            }
            code = zeroStr.concat(codeStr);
            code = "M".concat(code);
        }
        return code;
    }

    @Override
    public RestResult saveMessageTemplate(MessageTemplateModel param) {
        ImMessageTemplate template = new ImMessageTemplate();
        BeanUtils.copyProperties(param, template);
        // 模板编码
        String code = generateCode();
        template.setTemplateCode(code);
        template.setCreateTime(new Date());
        imMessageTemplateMapper.insert(template);
        return RestResult.ok("保存成功");
    }

    @Override
    public RestResult updateMessageTemplate(MessageTemplateModel param) {
        ImMessageTemplate template = new ImMessageTemplate();
        BeanUtils.copyProperties(param, template);
        Example example = new Example(ImMessageTemplate.class);
        example.createCriteria().andEqualTo("templateCode", param.getTemplateCode());
        template.setUpdateTime(new Date());
        imMessageTemplateMapper.updateByExampleSelective(template, example);
        return RestResult.ok("更新成功");
    }

    @Override
    public RestResult deleteMessageTemplate(MessageTemplateParam param) {
        String code = param.getTemplateCode();
        Example example = new Example(ImMessageTemplate.class);
        example.createCriteria().andEqualTo("templateCode", param.getTemplateCode());
        imMessageTemplateMapper.deleteByExample(example);
        return RestResult.ok("删除成功");
    }

    @Override
    public RestResult messageTemplateDetail(MessageTemplateParam param) {
        Example example = new Example(ImMessageTemplate.class);
        example.createCriteria().andEqualTo("templateCode", param.getTemplateCode());
        List<ImMessageTemplate> list = imMessageTemplateMapper.selectByExample(example);
        if (list.size() > 0)
            return RestResult.ok(list.get(0));
        else
            return RestResult.failure("没有查询到数据");
    }
}
