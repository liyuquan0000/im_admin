package com.jdd.imadmin.api.service.impl;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.OutputGroupMemberList;
import cn.wildfirechat.sdk.GroupAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;
import com.jdd.imadmin.api.service.GroupMemberService;
import com.jdd.imadmin.api.service.TokenVerifyService;
import com.jdd.imadmin.common.enums.ApiErrorCode;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2020-03-03 10:47
 ************************************************************/

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private TokenVerifyService tokenVerifyService;
    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Override
    public ResultModel setGroupManager(GroupMemberSetParam param) {
        if (!tokenVerifyService.verify(param.getToken())) {
            return ResultModel.accessTokenInvalid();
        }
        List<String> list = new ArrayList<>();

        String adminOpenId = param.getAdminOpenId();
        String groupOpenId = param.getGroupOpenId();
        String merchantOpenId=param.getMerchantOpenId();
        Oauth2UserToken admin = oAuth2UserService.getUserOauthInfoByOpenId(adminOpenId);
        Oauth2UserToken merchant = oAuth2UserService.getUserOauthInfoByOpenId(merchantOpenId);
        if (admin == null) {
            return ResultModel.openIdInvalid();
        }
        list.add(admin.getUserId());
        if (param.getManager() == null) {
            param.setManager(true);
        }
        try {
            IMResult<Void> imResult = GroupAdmin.setGroupManager(merchant.getUserId(), groupOpenId, list, param.getManager(), param.getToLines(), param.getNotifyMessage());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_GROUP_MANAGER_CAP) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_GROUP_MANAGER_CAP);
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_IN_GROUP) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_NOT_GROUP_MEMBER);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("modifyGroup,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel transferGroupManager(GroupManagerTransParam param) {
        if (!tokenVerifyService.verify(param.getToken())) {
            return ResultModel.accessTokenInvalid();
        }
        try {
            Oauth2UserToken merchantToken = oAuth2UserService.getUserOauthInfoByOpenId(param.getMerchantOpenId());
            if (merchantToken == null) {
                return ResultModel.openIdInvalid();
            }

            Oauth2UserToken newUserToken = oAuth2UserService.getUserOauthInfoByOpenId(param.getNewOwnerOpenId());
            if (newUserToken == null) {
                return ResultModel.openIdInvalid();
            }

            IMResult<Void> imResult = GroupAdmin.transferGroup("", param.getGroupOpenId(), newUserToken.getUserId(), param.getToLines(), param.getNotifyMessage());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_IN_GROUP) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_NOT_GROUP_MEMBER);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("modifyGroup,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel addGroupMember(GroupMemberAddParam param) {

        if (!tokenVerifyService.verify(param.getToken())) {
            return ResultModel.accessTokenInvalid();
        }
        Oauth2UserToken memberToken = oAuth2UserService.getUserOauthInfoByOpenId(param.getAddMemberOpenId());
        if (memberToken == null) {
            return ResultModel.openIdInvalid();
        }
        List<String> list = new ArrayList<>();
        list.add(memberToken.getUserId());
        try {
            IMResult<Void> imResult = GroupAdmin.addGroupMembers("", param.getGroupOpenId(), list, param.getToLines(), param.getNotifyMessage());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_GROUP_MANAGER_CAP) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_GROUP_COUNT_CAP);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("modifyGroup,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel deleteGroupMember(GroupMemberDeleteParam param) {
        if (!tokenVerifyService.verify(param.getToken())) {
            return ResultModel.accessTokenInvalid();
        }

        Oauth2UserToken memberToken = oAuth2UserService.getUserOauthInfoByOpenId(param.getMemberOpenId());
        if (memberToken == null) {
            return ResultModel.openIdInvalid();
        }
        List<String> list = new ArrayList<>();
        list.add(memberToken.getUserId());
        try {
            IMResult<Void> imResult = GroupAdmin.kickoffGroupMembers("", param.getGroupOpenId(), list, param.getToLines(), param.getNotifyMessage());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            }else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_IN_GROUP) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_NOT_GROUP_MEMBER);
            }else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_GROUP_MANAGER_CAP) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_GROUP_MANAGER_CAP);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("modifyGroup,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel getGroupMembers(GetGroupMembersParam param) {
        Oauth2Token oauth2Token = tokenVerifyService.getTokenDetail(param.getToken());
        if (oauth2Token == null) {
            return ResultModel.accessTokenInvalid();
        }
        Oauth2UserToken memberToken = oAuth2UserService.getUserOauthInfoByOpenId(param.getMerchantOpenId());
        if (memberToken == null) {
            return ResultModel.openIdInvalid();
        }
        try {
            IMResult<OutputGroupMemberList> imResult = GroupAdmin.getGroupMembers(param.getGroupOpenId());

            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                imResult.getResult().getMembers().stream().forEach(e ->
                        e.setMember_id(oAuth2UserService.generateOpenId(e.getMember_id(), oauth2Token.getClientId())));
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("modifyGroup,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


}
