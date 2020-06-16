package com.jdd.imadmin.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.jdd.imadmin.dao.base.PageInfo;
import com.jdd.imadmin.dao.base.RestResult;
import com.jdd.imadmin.dao.entity.Oauth2Client;
import com.jdd.imadmin.dao.mapper.Oauth2ClientMapper;
import com.jdd.imadmin.manage.controller.model.oauth2Client.ClientDownModel;
import com.jdd.imadmin.manage.controller.model.oauth2Client.ListOauth2Client;
import com.jdd.imadmin.manage.controller.param.PageParam;
import com.jdd.imadmin.manage.controller.param.oauth2Client.*;
import com.jdd.imadmin.manage.service.Oauth2ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class Oauth2ClientServiceImpl implements Oauth2ClientService {

    @Autowired
    private Oauth2ClientMapper oauth2ClientMapper;

    public static List<ListOauth2Client> convert(List<Oauth2Client> source, List<ListOauth2Client> target) {
        for (Oauth2Client provider : source) {
            ListOauth2Client model = new ListOauth2Client();
            BeanUtils.copyProperties(provider, model);
            target.add(model);
        }
        return target;
    }

    @Override
    public PageInfo listOauth2Client(PageParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Oauth2Client> list = oauth2ClientMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    private String randomGenerate(int length) {
        Random random = new Random();
        StringBuffer valSb = new StringBuffer();
        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        int charLength = charStr.length();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }

        return valSb.toString();
    }

    private String generateCode() {
        String code = "1000001";
        String maxCode = oauth2ClientMapper.selectMaxCode();
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
            code = "1".concat(code);
        }
        return code;
    }

    @Override
    public RestResult saveOauth2Client(SaveImTechnologyProvider param) {
        Oauth2Client client = new Oauth2Client();
        BeanUtils.copyProperties(param, client);
        // 编号
        client.setClientNumber(generateCode());
        // 配置信息
        client.setClientId(randomGenerate(18));
        client.setClientSecret(randomGenerate(90));
        // 用户accessToken有效期,默认一天
        client.setAccessTokenValidity(60 * 60 * 24);
        client.setAppAccessTokenValidity(60 * 60 * 24);
        client.setRefreshTokenValidity(60 * 60 * 24 * 3);

        client.setCreateTime(new Date());
        oauth2ClientMapper.insert(client);
        return RestResult.ok("创建成功");
    }

    @Override
    public RestResult updateWebServerRedirectUri(UpdateWebServerRedirectUri param) {
        Oauth2Client client = new Oauth2Client();
        client.setWebServerRedirectUri(param.getWebServerRedirectUri());
        client.setUpdateTime(new Date());
        Example example = new Example(Oauth2Client.class);
        example.createCriteria().andEqualTo("clientNumber", param.getClientNumber());
        oauth2ClientMapper.updateByExampleSelective(client, example);
        return RestResult.ok("更新成功");
    }

    @Override
    public RestResult ipWhiteListDetail(Oauth2ClientParam param) {
        Example example = new Example(Oauth2Client.class);
        example.createCriteria().andEqualTo("clientNumber", param.getClientNumber());
        List<Oauth2Client> list = oauth2ClientMapper.selectByExample(example);
        if (list.size() > 0) {
            Oauth2Client provider = list.get(0);
            UpdateIpWhiteList ipDetail = new UpdateIpWhiteList();
            if (provider.getIpWhiteList() == null) {
                return RestResult.ok(null);
            } else {
                ipDetail.setNumber(provider.getClientNumber());
                ipDetail.setIp(provider.getIpWhiteList());
                return RestResult.ok(ipDetail);
            }
        } else
            return RestResult.failure("没有查询到数据");
    }

    @Override
    public RestResult updateIpWhiteList(UpdateIpWhiteList param) {
        Oauth2Client client = new Oauth2Client();
        client.setIpWhiteList(param.getIp());
        client.setUpdateTime(new Date());
        Example example = new Example(Oauth2Client.class);
        example.createCriteria().andEqualTo("clientNumber", param.getNumber());
        oauth2ClientMapper.updateByExampleSelective(client, example);
        return RestResult.ok("更新成功");
    }

    @Override
    public RestResult clientDown() {
        List<ClientDownModel> list = new ArrayList<>();
        List<Oauth2Client> clients = oauth2ClientMapper.selectAll();
        if (clients.size() > 0) {
            for (Oauth2Client client : clients) {
                ClientDownModel clientDownModel = new ClientDownModel();
                BeanUtils.copyProperties(client, clientDownModel);
                list.add(clientDownModel);
            }
        }
        return RestResult.ok(list);
    }

    @Override
    public RestResult updateOauth2Client(UpdateOauth2Client param) {
        Oauth2Client client = new Oauth2Client();
        BeanUtils.copyProperties(param, client);
        client.setUpdateTime(new Date());
        Example example = new Example(Oauth2Client.class);
        example.createCriteria().andEqualTo("clientNumber", param.getClientNumber());
        oauth2ClientMapper.updateByExampleSelective(client, example);
        return RestResult.ok("成功");
    }

    @Override
    public RestResult deleteOauth2Client(Oauth2ClientParam param) {
        Example example = new Example(Oauth2Client.class);
        example.createCriteria().andEqualTo("clientNumber", param.getClientNumber());
        oauth2ClientMapper.deleteByExample(example);
        return RestResult.ok("成功");
    }

    @Override
    public RestResult oauth2ClientDetail(Oauth2ClientParam param) {
        Oauth2Client oauth2Client = new Oauth2Client();
        Example example = new Example(Oauth2Client.class);
        example.createCriteria().andEqualTo("clientNumber", param.getClientNumber());
        List<Oauth2Client> list = oauth2ClientMapper.selectByExample(example);
        if (list.size() > 0) {
            oauth2Client = list.get(0);
            UpdateOauth2Client client = new UpdateOauth2Client();
            BeanUtils.copyProperties(oauth2Client, client);
            return RestResult.ok(client);
        } else
            return RestResult.failure("没有查询到数据");
    }


}
