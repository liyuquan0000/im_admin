package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author @sailength on 2020/2/20.
 */
@Data
public class QueryAutoReplyDto extends BasePageDTO {


    private static final long serialVersionUID = -2976067936240443528L;
    @NotEmpty(message = "token 不能为空!")
    @JSONField(name = "token")
    private String token;
    @NotEmpty(message = "merchant_open_id 不能为空!")
    @JSONField(name = "merchant_open_id")
    private String merchant_open_id;
    @NotEmpty(message = "serviceOpenId 不能为空!")
    @JSONField(name = "service_open_id")
    private String serviceOpenId;

}
