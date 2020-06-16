package com.jdd.imadmin.manage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @sailength on 2020/3/31.
 */
@RestController
public class HealthController {

    /**
     * nginx健康监测接口
     *
     * @return
     */
    @RequestMapping(value = "/getHealth")
    public String getHealth() {
        return "200";
    }

}
