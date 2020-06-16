package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author @sailength on 2020/2/20.
 */
@Data
public class DeleteAutoReplyDto implements Serializable {

    private static final long serialVersionUID = 593860486083094635L;

    @NotEmpty(message = "token 不能为空!")
    @JSONField(name = "token")
    private String token;
    @NotEmpty(message = "merchantOpenId 不能为空!")
    @JSONField(name = "merchant_open_id")
    private String merchantOpenId;
    @NotEmpty(message = "serviceOpenId 不能为空!")
    @JSONField(name = "service_open_id")
    private String serviceOpenId;
    @NotNull(message = "replyId 不能为空!")
    @JSONField(name = "reply_id")
    private Long replyId;


}
