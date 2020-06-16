package com.jdd.imadmin.api.controller;


import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;
import com.jdd.imadmin.api.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "public/group", produces = "application/json;charset=UTF-8")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    /**
     * 获取群审批列表
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/getGroupApproveList")
    public ResultModel getGroupApproveList(@RequestBody @Valid GroupMemberApplyParam param) {
        return groupService.getGroupApproveList(param);
    }


    /**
     * 群审批通过
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/approveGroupMemberApply")
    public ResultModel approveGroupMemberApply(@RequestBody @Valid GroupMemberApplyApproveParam param) {
        return groupService.approveGroupMemberApply(param);
    }

    /**
     * 获取商户群列表
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/getMerchantGroupList")
    public ResultModel getMerchantGroupList(@RequestBody @Valid MerchantGroupParam param) {
        return groupService.getMerchantGroupList(param);
    }

    /**
     * @param param
     * @return
     */
    @PostMapping(value = "/createGroup")
    public ResultModel createGroup(@RequestBody @Valid CreateGroupParam param) {
        return groupService.createGroup(param);
    }

    @PostMapping(value = "/getGroupQRCode")
    public ResultModel getGroupQRCode(@RequestBody @Valid GetGroupQRCodeParam getGroupQRCodeParam) {
        return groupService.getQrCodeString(getGroupQRCodeParam);
    }


    /**
     * 解散群
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/dismissGroup")
    public ResultModel dismissGroup(@RequestBody @Valid DismissGroupParam param) {
        return groupService.dismissGroup(param);
    }

    @PostMapping(value = "/modifyGroup")
    public ResultModel modifyGroup(@RequestBody @Valid ModifyGroupParam param) {
        return groupService.modifyGroup(param);
    }


}
