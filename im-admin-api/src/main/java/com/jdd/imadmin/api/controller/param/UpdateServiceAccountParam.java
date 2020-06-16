package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
public class UpdateServiceAccountParam implements Serializable {

    @NotEmpty(message = "token 不能为空!")
    private String token;
    @NotEmpty(message = "serviceOpenId 不能为空!")
    private String serviceOpenId;
    @NotEmpty(message = "merchantOpenId 不能为空!")
    private String merchantOpenId;

    private String name;

    private String headPic;

    private String description;

    private Integer status;

    private String welcome;

    private ServiceMenuListParam menuJson;


}