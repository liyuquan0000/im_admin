package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class VersionParam implements Serializable {


    @NotNull(message="platform 不能为空!")
    private Integer platform;
}
