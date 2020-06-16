package com.jdd.imadmin.api.service;

import com.jdd.imadmin.api.common.BasePageModel;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;
import com.jdd.imadmin.api.service.dto.*;

/**
 * @author @sailength on 2020/2/20.
 */
public interface ServiceAccountService {
    ResultModel<BasePageModel<UserInfoDto>> queryWatchList(QueryWatchListDto queryWatchListDto);

    ResultModel<Integer> addAutoReply(AddAutoReplyDto addAutoReplyDto);

    ResultModel<BasePageModel<ReplyInfoDto>> queryAutoReply(QueryAutoReplyDto queryAutoReplyDto);

    ResultModel deleteAutoReply(DeleteAutoReplyDto deleteAutoReplyDto);

    ResultModel updateAutoReply(UpdateAutoReplyDto updateAutoReplyDto);

    ResultModel createServiceAccount(ServiceAccountParam param);

    ResultModel updateServiceAccount(UpdateServiceAccountParam param);

    ResultModel listServiceAccount(QueryServiceAccountParam param);

    ResultModel sendMessage(MessageParam param);

    ResultModel<String> getQrCode(GetServiceAccountQRCodeParam param);
}
