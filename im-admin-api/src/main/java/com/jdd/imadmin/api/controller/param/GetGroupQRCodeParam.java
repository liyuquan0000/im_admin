package com.jdd.imadmin.api.controller.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author @sailength on 2020/3/23.
 */
@Data
public class GetGroupQRCodeParam implements Serializable {

    private static final long serialVersionUID = 9060921423179009546L;

    @NotEmpty(message="token 不能为空!")
    @JSONField(name="token")
    private String token;
    @NotEmpty(message="groupId 不能为空!")
    @JSONField(name="group_id")
    private String groupId;
}
