package com.jdd.imadmin.api.service;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;

/************************************************************
 * @Description: TODO
 * @Author: zhengrui
 * @Date 2020-03-03 10:44
 ************************************************************/

public interface GroupMemberService {
    ResultModel setGroupManager(GroupMemberSetParam param);

    ResultModel transferGroupManager(GroupManagerTransParam param);

    ResultModel addGroupMember(GroupMemberAddParam param);

    ResultModel deleteGroupMember(GroupMemberDeleteParam param);

    ResultModel getGroupMembers(GetGroupMembersParam param);
}
