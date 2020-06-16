package com.jdd.imadmin.api.component;

import cn.wildfirechat.sdk.utilities.AdminHttpUtils;
import cn.wildfirechat.sdk.utilities.ChannelHttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/************************************************************
 * @Description: IMServer配置初始化
 * @Author: zhengrui
 * @Date 2020-02-25 10:51
 ************************************************************/

@Component
public class IMServerHttpConf {

    @Value("${im.server.admin.host}")
    private String imServerAdminHost;

    @Value("${im.server.secret}")
    private String imServerSecret;

    @Value("${im.server.channel.host}")
    private String imServerChannelHost;


    @PostConstruct
    public void init() {
        AdminHttpUtils.init(imServerAdminHost, imServerSecret);
        ChannelHttpUtils.init(imServerChannelHost);
    }

}
