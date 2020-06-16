package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GroupMemberApplyApproveParam extends BaseParam {

    @NotEmpty(message = "token不能为空!")
    private String token;
    /**
     * 操作人openid
     */
    @NotEmpty(message="merchantOpenId不能为空!")
    private String merchantOpenId;
    /**
     * 主键
     */
    @NotNull(message="id操作记录主键不能为空!")
    private Long id;
    @NotNull(message="operateStatus不能为空!")
    private Integer operateStatus;
    @NotEmpty(message="memberOpenId不能为空!")
    private String memberOpenId;
    @NotEmpty(message="groupOpenId不能为空!")
    private String groupOpenId;

}
