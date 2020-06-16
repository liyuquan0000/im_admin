package com.jdd.imadmin.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author @sailength on 2020/5/13.
 */

@Controller
public class MockController {

    @PostMapping(value = "/test")
    public void test(String code) {
        System.out.println(code);
    }
}
