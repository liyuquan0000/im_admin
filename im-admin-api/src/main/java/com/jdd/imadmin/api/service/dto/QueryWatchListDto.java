package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author @sailength on 2020/2/20.
 *         <p>
 *         查询关注列表实体
 */

@Data
public class QueryWatchListDto extends BasePageDTO {

    @NotEmpty(message="token 不能为空!")
    @JSONField(name = "token")
    private String token;

    /**
     * 服务号openid
     */
    @NotEmpty(message="serviceOpenId 不能为空!")
    @JSONField(name = "service_open_id")
    private String serviceOpenId;

    /**
     * 商户openId
     */
    @NotEmpty(message="merchantOpenId 不能为空!")
    @JSONField(name = "merchant_open_id")
    private String merchantOpenId;

}
