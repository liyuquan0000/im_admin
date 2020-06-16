package com.jdd.imadmin.api.controller.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author @sailength on 2020/3/23.
 */
@Data
public class GetServiceAccountQRCodeParam implements Serializable {

    private static final long serialVersionUID = 6573052700621958376L;
    @NotEmpty(message="token 不能为空!")
    @JSONField(name = "token")
    private String token;
    @NotEmpty(message="serviceId 不能为空!")
    @JSONField(name = "service_id")
    private String serviceId;
}
