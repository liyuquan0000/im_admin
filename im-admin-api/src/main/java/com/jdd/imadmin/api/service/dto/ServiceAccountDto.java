package com.jdd.imadmin.api.service.dto;

import com.jdd.imadmin.api.controller.param.ServiceMenuListParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
public class ServiceAccountDto implements Serializable {

    @NotEmpty(message = "serviceOpenId 不能为空!")
    private String serviceOpenId;
    @NotEmpty(message = "merchantOpenId 不能为空!")
    private String merchantOpenId;

    private String name;

    private String headPic;

    private String description;

    private String welcome;

    private ServiceMenuListParam menuJson;


}