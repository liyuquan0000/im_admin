package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author @sailength on 2020/4/15.
 */
@Data
public class QueryServiceAccountParam implements Serializable {

    private static final long serialVersionUID = 5021233265455282884L;
    @NotEmpty(message = "token 不能为空!")
    private String token;

    private String serviceOpenId;

    @NotEmpty(message = "merchantOpenId 不能为空!")
    private String merchantOpenId;
    
}
