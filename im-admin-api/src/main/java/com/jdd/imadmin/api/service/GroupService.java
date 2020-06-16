package com.jdd.imadmin.api.service;


import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;

public interface GroupService {


    ResultModel getGroupApproveList(GroupMemberApplyParam param);

    ResultModel approveGroupMemberApply(GroupMemberApplyApproveParam param);

    ResultModel createGroup(CreateGroupParam param);

    ResultModel dismissGroup(DismissGroupParam param);

    ResultModel getMerchantGroupList(MerchantGroupParam param);

    ResultModel modifyGroup(ModifyGroupParam param);

    ResultModel addQRCode(QRCodeParam param);

    ResultModel getQRCode(QRCodeParam param);

    ResultModel getQrCodeString(GetGroupQRCodeParam getGroupQRCodeParam);
}
