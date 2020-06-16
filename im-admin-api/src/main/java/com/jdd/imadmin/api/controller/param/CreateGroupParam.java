package com.jdd.imadmin.api.controller.param;

import com.jdd.imadmin.api.service.dto.GroupMemberDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateGroupParam extends BaseParam {

    /**
     * token
     */
    @NotEmpty(message="token不能为空!")
    private String token;
    @NotEmpty(message="groupName不能为空!")
    private String groupName;
    @NotEmpty(message="merchantOpenId不能为空!")
    private String merchantOpenId;

    private List<GroupMemberDTO> memberOpenIdArray;

    private Integer type;

    private String portrait;

    private String extra;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMerchantOpenId() {
        return merchantOpenId;
    }

    public void setMerchantOpenId(String merchantOpenId) {
        this.merchantOpenId = merchantOpenId;
    }

    public List<GroupMemberDTO> getMemberOpenIdArray() {
        return memberOpenIdArray;
    }

    public void setMemberOpenIdArray(List<GroupMemberDTO> memberOpenIdArray) {
        this.memberOpenIdArray = memberOpenIdArray;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
