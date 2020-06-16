package com.jdd.imadmin.api.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ServiceSubscriptionMessageDTO implements Serializable {

    /**
     * 服务号编号
     */
    private String serviceOpenId;

    /**
     * 商户openId
     */
    private String merchantOpenId;

    /**
     * 接受者的open_id
     */
    private String toUserId;

    Map<String, Object> content;

}
