package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
public class ServiceAccountParam implements Serializable {

    @NotEmpty(message = "token 不能为空!")
    private String token;

    private String serviceOpenId;

    @NotEmpty(message = "merchantOpenId 不能为空!")
    private String merchantOpenId;
    @NotEmpty(message = "name 不能为空!")
    private String name;
    @NotEmpty(message = "headPic 不能为空!")
    private String headPic;
    @NotEmpty(message = "description 不能为空!")
    private String description;

    private Integer status;

    private String welcome;
    //    @NotNull(message="ServiceMenuListParam 不能为空!")
    private ServiceMenuListParam menuJson;


}