package com.jdd.imadmin.api.controller.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ModifyGroupParam extends BaseParam {

    /**
     * token
     */

    @NotEmpty(message = "token 不能为空!")
    private String token;
    @NotEmpty(message = "groupId 不能为空!")
    private String groupId;

    /**
     * int Modify_Group_Name = 0;
     * int Modify_Group_Portrait = 1;
     * int Modify_Group_Extra = 2;
     * int Modify_Group_Mute = 3;
     * int Modify_Group_JoinType = 4;
     * int Modify_Group_PrivateChat = 5;
     * int Modify_Group_Searchable = 6;
     */
    @NotNull(message = "type 不能为空!")
    private Integer type;
    @NotEmpty(message = "value 不能为空!")
    private String value;
    @NotEmpty(message = "openId 不能为空!")
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
