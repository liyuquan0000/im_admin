package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GroupMemberDeleteParam extends BaseParam {


    @NotEmpty(message="token 不能为空!")
    private String token;
    @NotEmpty(message="groupOpenId 不能为空!")
    private String groupOpenId;
    @NotEmpty(message="memberOpenId 不能为空!")
    private String memberOpenId;
    @NotEmpty(message="merchantOpenId 不能为空!")
    private String merchantOpenId;
}
