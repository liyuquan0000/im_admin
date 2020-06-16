package com.jdd.imadmin.manage.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySources;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author @sailength on 2020/4/7.
 */
@NacosPropertySources({
        @NacosPropertySource(groupId = "im_admin", dataId = "application.properties"),
        @NacosPropertySource(groupId = "im_admin", dataId = "manage.properties")
})
@Data
@Component
public class CommonNacosConfig {
}
