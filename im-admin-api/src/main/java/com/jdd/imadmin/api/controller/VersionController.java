package com.jdd.imadmin.api.controller;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.VersionParam;
import com.jdd.imadmin.api.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/version", produces = "application/json;charset=UTF-8")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @CrossOrigin
    @PostMapping("/getVersionDetail")
    public ResultModel getVersionDetail(@RequestBody @Valid VersionParam param) {
        return versionService.getVersionDetail(param);
    }


}
