package com.jdd.imadmin.api.controller;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;
import com.jdd.imadmin.api.service.ServiceAccountService;
import com.jdd.imadmin.api.service.TokenVerifyService;
import com.jdd.imadmin.api.service.dto.*;
import com.jdd.imadmin.common.enums.ApiErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * @author @sailength on 2020/2/20.
 *         服务号api
 */
@RestController
@RequestMapping(value = "/public/serviceAccount")
public class ServiceAccountController {


    @Autowired
    private ServiceAccountService serviceAccountService;
    @Autowired
    private TokenVerifyService verifyService;

    /**
     * 查询关注列表
     */
    @PostMapping(value = "/queryWatchList")
    public ResultModel queryWatchList(@RequestBody @Valid QueryWatchListDto queryWatchListDto) {
        return serviceAccountService.queryWatchList(queryWatchListDto);
    }


    /**
     * 删除自动回复
     */
    @PostMapping(value = "/deleteAutoReply")
    public ResultModel deleteAutoConfig(@RequestBody DeleteAutoReplyDto deleteAutoReplyDto) {
        if (!verifyService.verify(deleteAutoReplyDto.getToken())) return ResultModel.accessTokenInvalid();
        return serviceAccountService.deleteAutoReply(deleteAutoReplyDto);
    }


    @PostMapping(value = "/addAutoReply")
    public ResultModel addAutoConfig(@RequestBody @Valid AddAutoReplyDto addAutoReplyDto) {
        if (!verifyService.verify(addAutoReplyDto.getToken())) return ResultModel.accessTokenInvalid();
        return serviceAccountService.addAutoReply(addAutoReplyDto);
    }

    @PostMapping(value = "/updateAutoReply")
    public ResultModel updateAutoReply(@RequestBody UpdateAutoReplyDto updateAutoReplyDto) {
        if (!verifyService.verify(updateAutoReplyDto.getToken())) return ResultModel.accessTokenInvalid();
        return serviceAccountService.updateAutoReply(updateAutoReplyDto);
    }

    @PostMapping(value = "/queryAutoReply")
    public ResultModel queryAutoReplyConfig(@RequestBody @Valid QueryAutoReplyDto queryAutoReplyDto) {
        if (!verifyService.verify(queryAutoReplyDto.getToken())) return ResultModel.accessTokenInvalid();
        return serviceAccountService.queryAutoReply(queryAutoReplyDto);
    }


    /**
     * 创建服务号
     */
    @PostMapping(value = "/save")
    public ResultModel saveServiceAccount(@RequestBody @Valid ServiceAccountParam param) {
        if (!verifyService.verify(param.getToken())) return ResultModel.accessTokenInvalid();
        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getMerchantOpenId())) {
            return ResultModel.parameterNull("商户open_id不能为空");
        }

        return serviceAccountService.createServiceAccount(param);
    }


    @PostMapping(value = "/getServiceAccountQRCode")
    public ResultModel getServiceAccountQRCode(@RequestBody @Valid GetServiceAccountQRCodeParam param) {
        if (!verifyService.verify(param.getToken())) {
            return ResultModel.accessTokenInvalid();
        }
        return serviceAccountService.getQrCode(param);
    }

    /**
     * 修改服务号
     */
    @PostMapping(value = "/update")
    public ResultModel updateServiceAccount(@RequestBody @Valid UpdateServiceAccountParam param) {
        if (!verifyService.verify(param.getToken())) {
            return ResultModel.accessTokenInvalid();
        }
        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getServiceOpenId())) {
            return ResultModel.parameterNull("服务open_id不能为空");
        }

        return serviceAccountService.updateServiceAccount(param);
    }


    /**
     * 商户的服务号列表
     */
    @PostMapping(value = "/list")
        public ResultModel listServiceAccount(@RequestBody @Valid QueryServiceAccountParam param) {

        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getMerchantOpenId())) {
            return ResultModel.parameterNull("商户open_id不能为空");
        }

        return serviceAccountService.listServiceAccount(param);
    }


    /**
     * 服务号发送订阅消息
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/sendMessage")
    public ResultModel getUserInfo(@RequestBody @Valid MessageParam param) {
        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }
        try {
            return serviceAccountService.sendMessage(param);
        } catch (Exception e) {
            return ResultModel.newErrorModel(ApiErrorCode.ERROR, ExceptionUtils.getStackTrace(e));
        }
    }

}
