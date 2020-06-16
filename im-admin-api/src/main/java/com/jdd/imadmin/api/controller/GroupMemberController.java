package com.jdd.imadmin.api.controller;


import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;
import com.jdd.imadmin.api.service.GroupMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "public/groupmember", produces = "application/json;charset=UTF-8")
public class GroupMemberController {

    private static final Logger logger = LoggerFactory.getLogger(GroupMemberController.class);

    @Autowired
    private GroupMemberService groupMemberService;

    /**
     * 设置群管理
     */
    @PostMapping(value = "/setGroupManager")
    public ResultModel setGroupManager(@RequestBody @Valid GroupMemberSetParam param) {
        return groupMemberService.setGroupManager(param);
    }

    /**
     * 转移群主
     */
    @PostMapping(value = "/transferGroupManager")
    public ResultModel transferGroupManager(@RequestBody @Valid GroupManagerTransParam param) {
        return groupMemberService.transferGroupManager(param);
    }

    /**
     * 获取群成员列表
     */
    @PostMapping(value = "/getGroupMemberList")
    public ResultModel getGroupMemberList(@RequestBody @Valid GetGroupMembersParam param) {
        return groupMemberService.getGroupMembers(param);
    }

    /**
     * 将某个用户拉入群
     */
    @PostMapping(value = "/addGroupMember")
    public ResultModel addGroupMember(@RequestBody @Valid GroupMemberAddParam param) {
        return groupMemberService.addGroupMember(param);
    }

    /**
     * 删除某个群成员
     */
    @PostMapping(value = "/deleteGroupMember")
    public ResultModel deleteGroupMember(@RequestBody @Valid GroupMemberDeleteParam param) {
        return groupMemberService.deleteGroupMember(param);
    }


}
