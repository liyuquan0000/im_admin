package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MessageParam implements Serializable {

    /**
     * token
     */
    @NotEmpty(message = "token 不能为空!")
    private String token;

    /**
     * 商户openId
     */
    @NotEmpty(message = "merchantOpenId 不能为空!")
    private String merchantOpenId;


    /**
     * 服务号编号
     */
    @NotEmpty(message = "serviceOpenId 不能为空!")
    private String serviceOpenId;

    /**
     * 接受者的open_id
     */
    private String toUserId;

    /**
     * 1 群推，2.单推
     */
    @NotNull(message = "pushType 不能为空")
    private int pushType;


    @NotEmpty(message = "content 不能为空")
    private String content;

    @NotNull(message = "messageType 不能为空")
    private Integer messageType;

}
