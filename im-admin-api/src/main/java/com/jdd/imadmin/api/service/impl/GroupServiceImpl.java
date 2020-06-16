package com.jdd.imadmin.api.service.impl;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.OutputCreateGroupResult;
import cn.wildfirechat.pojos.PojoGroupInfo;
import cn.wildfirechat.pojos.PojoGroupMember;
import cn.wildfirechat.sdk.GroupAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import com.jdd.im.sdk.MerchantGroupAdmin;
import com.jdd.im.sdk.model.PageResult;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;
import com.jdd.imadmin.api.service.GroupService;
import com.jdd.imadmin.api.service.TokenVerifyService;
import com.jdd.imadmin.api.service.dto.CreateGroupDTO;
import com.jdd.imadmin.api.service.dto.GroupMemberDTO;
import com.jdd.imadmin.common.enums.ApiErrorCode;
import com.jdd.imadmin.common.util.QrCodeUtils;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.dao.entity.Oauth2UserToken;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import com.jdd.imserver.pojos.group.GroupMemberApplyPoJo;
import com.jdd.imserver.pojos.group.MerchantGroupPageReq;
import com.jdd.imserver.pojos.group.MerchantGroupPoJo;
import com.jdd.imserver.pojos.group.OutPutGetQRCode;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private TokenVerifyService verifyService;

    @Autowired
    private OAuth2UserService oAuth2UserService;


    @Override
    public ResultModel getGroupApproveList(GroupMemberApplyParam param) {
        Oauth2Token oauth2Token = verifyService.getTokenDetail(param.getToken());
        if (oauth2Token == null) {
            return ResultModel.accessTokenInvalid();
        }
        GroupMemberApplyPoJo poJo = new GroupMemberApplyPoJo();
        poJo.setStatus(0);
        poJo.setGid(param.getGroupOpenId());
        try {
            IMResult<PageResult<GroupMemberApplyPoJo>> imResult = GroupAdmin.getGroupApproveList(param.getGroupOpenId(), param.getPageNo(), param.getPageSize());
            if (imResult != null && imResult.getCode() == ErrorCode.ERROR_CODE_SUCCESS.getCode()) {
                imResult.getResult().getList().forEach(e -> {
                    String uid = e.getMid();
                    String openId = oAuth2UserService.generateOpenId(uid, oauth2Token.getClientId());
                    e.setMid(openId);
                });
            }  else {
                return ResultModel.parameterAbnormality();
            }
            return ResultModel.newSuccessModel(imResult.getResult());
        } catch (Exception e) {
            logger.error("getGroupApproveList,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel approveGroupMemberApply(GroupMemberApplyApproveParam param) {
        Oauth2Token oauth2Token = verifyService.getTokenDetail(param.getToken());
        if (oauth2Token == null) {
            return ResultModel.accessTokenInvalid();
        }
        try {
            Oauth2UserToken merchantOauth2UserToken = oAuth2UserService.getUserOauthInfoByOpenId(param.getMerchantOpenId());
            Oauth2UserToken userOauth2UserToken = oAuth2UserService.getUserOauthInfoByOpenId(param.getMemberOpenId());
            if (merchantOauth2UserToken == null||userOauth2UserToken==null) {
                return ResultModel.openIdInvalid();
            }
            IMResult<Void> imResult = GroupAdmin.approveGroupMemberApply(merchantOauth2UserToken.getUserId(), param.getId(), param.getOperateStatus(), param.getGroupOpenId(), userOauth2UserToken.getUserId(), param.getToLines(), param.getNotifyMessage());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel();
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_GROUP_MANAGER_CAP) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_GROUP_COUNT_CAP);
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_GROUP_APPLY_NOT_EXIST) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_GROUP_APPLY_NOT_EXIST);
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_APPROVED) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_APPROVED);
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_APPROVE_EXPIRED) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_APPROVE_EXPIRED);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("approveGroupMemberApply,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    public ResultModel getMerchantGroupList(MerchantGroupParam param) {
        try {
            boolean verify = verifyService.verify(param.getToken());
            if (!verify) {
                return ResultModel.accessTokenInvalid();
            }


            MerchantGroupPageReq req = new MerchantGroupPageReq();

            req.setMerchantId(param.getMerchantOpenId());
            req.setPageNo(param.getPageNo());
            req.setPageSize(param.getPageSize());
            IMResult<PageResult<MerchantGroupPoJo>> resultIMResult = MerchantGroupAdmin.getMerchantGroupList(req);
            if (resultIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(resultIMResult.getResult());
            } else if (resultIMResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else if (resultIMResult.getErrorCode() == ErrorCode.ERROR_CODE_GROUP_ALREADY_EXIST) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_GROUP_ALREADY_EXIST);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("getMerchantGroupList,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    @Transactional
    public ResultModel createGroup(CreateGroupParam param) {
//        获取用户信息
        try {
            boolean verify = verifyService.verify(param.getToken());
            if (!verify) {
                return ResultModel.accessTokenInvalid();
            }


            PojoGroupInfo group = new PojoGroupInfo();
            group.setName(param.getGroupName());
            group.setExtra(param.getExtra());
            /** 所有人为商户*/
            group.setOwner(param.getMerchantOpenId());
            group.setPortrait(param.getPortrait());
            group.setType(2);


            List<PojoGroupMember> pojoGroupMembers = new ArrayList<>();
            if (!CollectionUtils.isEmpty(param.getMemberOpenIdArray())) {
                for (GroupMemberDTO groupMemberDTO : param.getMemberOpenIdArray()) {
                    PojoGroupMember member = new PojoGroupMember();


                    String memberOpenId = groupMemberDTO.getMemberId();
                    Oauth2UserToken oauth2UserToken = oAuth2UserService.getUserOauthInfoByOpenId(memberOpenId);
                    if (oauth2UserToken == null) {
                        logger.error("openid={}不存在", memberOpenId);
                        throw new Exception("openId=" + memberOpenId + "不存在");
                    }
                    member.setMember_id(oauth2UserToken.getUserId());
                    member.setType(groupMemberDTO.getType());
                    member.setAlias(groupMemberDTO.getAlias());
                    pojoGroupMembers.add(member);
                }
            }
            IMResult<OutputCreateGroupResult> resultIMResult = GroupAdmin.createGroup(param.getMerchantOpenId(), group, pojoGroupMembers, param.getToLines(), param.getNotifyMessage());
            if (resultIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                String groupId=resultIMResult.getResult().getGroup_id();
                CreateGroupDTO createGroupDTO=new CreateGroupDTO();
                createGroupDTO.setGroupId(groupId);
                return ResultModel.newSuccessModel(createGroupDTO);
            } else if (resultIMResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else if (resultIMResult.getErrorCode() == ErrorCode.ERROR_CODE_GROUP_ALREADY_EXIST) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_GROUP_ALREADY_EXIST);
            } else {
                return ResultModel.parameterAbnormality();
            }

        } catch (Exception e) {
            logger.error("createGroup,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }

    }

    @Override
    public ResultModel dismissGroup(DismissGroupParam param) {
        try {

            boolean verify = verifyService.verify(param.getToken());
            if (!verify) {
                return ResultModel.accessTokenInvalid();
            }

            IMResult<Void> imResult = GroupAdmin.dismissGroup(param.getMerchantOpenId(), param.getGroupOpenId(), param.getToLines(), param.getNotifyMessage());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("dismissGroup,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel modifyGroup(ModifyGroupParam param) {
        try {

            boolean verify = verifyService.verify(param.getToken());
            if (!verify) {
                return ResultModel.accessTokenInvalid();
            }


            IMResult<Void> imResult = GroupAdmin.modifyGroupInfo(param.getOpenId(), param.getGroupId(), param.getType(), param.getValue(), param.getToLines());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
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


    @Override
    public ResultModel addQRCode(QRCodeParam param) {
        try {

            boolean verify = verifyService.verify(param.getToken());
            if (!verify) {
                return ResultModel.accessTokenInvalid();
            }

            IMResult<Void> imResult = GroupAdmin.addQRCode(param.getKey());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("addQRCode,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel getQRCode(QRCodeParam param) {
        try {

            boolean verify = verifyService.verify(param.getToken());
            if (!verify) {
                return ResultModel.accessTokenInvalid();
            }
            IMResult<OutPutGetQRCode> imResult = GroupAdmin.getQRCode(param.getKey());
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(imResult.getResult());
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("getQRCode,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }

    @Override
    public ResultModel getQrCodeString(GetGroupQRCodeParam getGroupQRCodeParam) {
        try {
            String uuid = UUID.randomUUID().toString();
            IMResult imResult = GroupAdmin.addQRCode(uuid);
            if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel(QrCodeUtils.getGroupQr(getGroupQRCodeParam.getGroupId(), uuid));
            } else if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_TIMEOUT) {
                return ResultModel.timeOut();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("get QRcode string err", e);
            return ResultModel.newErrorModel();
        }
    }


}
