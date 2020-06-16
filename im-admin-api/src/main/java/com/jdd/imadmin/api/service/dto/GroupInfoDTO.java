package com.jdd.imadmin.api.service.dto;

import java.util.List;

public class GroupInfoDTO {
    private String id;
    private String name;
    private String portrait;
    private String owner;
    private int type;
    private String extra;

    private List<GroupMemberDTO> groupMemberDTOS;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public List<GroupMemberDTO> getGroupMemberDTOS() {
        return groupMemberDTOS;
    }

    public void setGroupMemberDTOS(List<GroupMemberDTO> groupMemberDTOS) {
        this.groupMemberDTOS = groupMemberDTOS;
    }
}
