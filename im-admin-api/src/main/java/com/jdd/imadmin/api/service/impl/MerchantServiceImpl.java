package com.jdd.imadmin.api.service.impl;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.InputOutputUserInfo;
import cn.wildfirechat.pojos.OutputCreateUser;
import cn.wildfirechat.sdk.UserAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jdd.im.sdk.MerchantAdmin;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.MerchantParam;
import com.jdd.imadmin.api.controller.param.UserMerchantParam;
import com.jdd.imadmin.api.service.MerchantService;
import com.jdd.imadmin.api.service.dto.MerchantDto;
import com.jdd.imadmin.api.service.dto.UserInfoDto;
import com.jdd.imadmin.common.enums.ApiErrorCode;
import com.jdd.imadmin.common.enums.OpenStatusEnum;
import com.jdd.imadmin.common.enums.RelationEnum;
import com.jdd.imadmin.common.enums.StatusEnum;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import com.jdd.imserver.pojos.Merchant;
import com.jdd.imserver.pojos.MerchantUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);


    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Override
    public ResultModel saveMerchant(MerchantParam param) {
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(param, merchant);
        if (StringUtils.isEmpty(param.getMerchantId())) {
            param.setMerchantId(UUID.randomUUID().toString());
        }
        try {
            //新增商户用户
            InputOutputUserInfo user = new InputOutputUserInfo();
            /** 管理员名称*/
            user.setName(param.getMerchantName());
            user.setDisplayName(param.getUserNickName());
            user.setPortrait(param.getUserHeadUrl());
            user.setUserId(param.getMerchantId());
            IMResult<OutputCreateUser> userIdResult = UserAdmin.createUser(user);
            if (userIdResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_MERCHANT_MEMBER);
            }

            //新增商户
            String userId = userIdResult.getResult().getUserId();
            merchant.setMerchantOpenId(userId);
            merchant.setDescriptionUrl(JSONObject.toJSONString(param.getDescriptionUrl()));
            merchant.setOpenStatus(OpenStatusEnum.PASS.getValue());
            merchant.setStatus(StatusEnum.VALID.getValue());
            merchant.setCreateTime(new Date());
            merchant.setUpdateTime(new Date());
            merchant.setMerchantId(userId);
            IMResult<String> saveResult = MerchantAdmin.createMerchant(merchant);
            if (saveResult.getErrorCode() == ErrorCode.ERROR_CODE_ALREADY_EXIST) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_MERCHANT_ALREADY_EXIST);
            }
            if (saveResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newErrorModel("商户创建失败");
            }

            Map<String, String> map = new HashMap<>();
            /** 生成一条记录*/
            oAuth2UserService.generateMerchantUserOpenId(merchant.getMerchantId(), param.getAppid());
            map.put("merchant_open_id", merchant.getMerchantOpenId());
            return ResultModel.newSuccessModel(map);

        } catch (Exception e) {
            logger.error("saveMerchant,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    public ResultModel updateMerchant(MerchantParam param) {
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(param, merchant);
        merchant.setUpdateTime(new Date());

        try {
            //修改商户信息
            IMResult<String> updateResult = MerchantAdmin.updateMerchant(merchant);
            if (updateResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("updateMerchant,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    public ResultModel getMerchant(MerchantParam param) {

        try {
            IMResult<Merchant> merchantResult = MerchantAdmin.getMerchant(param.getMerchantId());
            if (merchantResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_MERCHANT_NOT_EXIST);
            }
            if (merchantResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                Merchant merchant = merchantResult.getResult();
                MerchantDto merchantDto = new MerchantDto();
                BeanUtils.copyProperties(merchant, merchantDto);

                //获取商户用户信息
                String openId = merchant.getMerchantOpenId();
                IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByUserId(openId);
                if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    InputOutputUserInfo userInfo = userResult.getResult();
                    merchantDto.setUserNickName(userInfo.getDisplayName());
                    merchantDto.setUserHeadUrl(userInfo.getPortrait());
                }
                if (StringUtils.isNotBlank(merchant.getDescriptionUrl())) {
                    String descriptionUrl = merchant.getDescriptionUrl();
                    merchantDto.setDescriptionUrl(JSONObject.parseArray(descriptionUrl, String.class));
                }

                return ResultModel.newSuccessModel(merchantDto);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("getMerchant,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    public ResultModel getMerchantUser(UserMerchantParam param) {

        /** 校验openid合法性*/
        if (!oAuth2UserService.isOpenExists(param.getOpenId())) {
            return ResultModel.openIdInvalid();
        }
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setMerchantOpenId(param.getMerchantOpenId());
        merchantUser.setOpenId(param.getOpenId());

        try {
            IMResult<MerchantUser> merchantUserResult = MerchantAdmin.getMerchantUser(merchantUser);
            if (merchantUserResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                Map<String, Object> map = new HashMap<String, Object>();
                if (merchantUserResult.getResult().getStatus().equals(RelationEnum.FRIEND.getValue())) {
                    map.put("has_relation", true);
                } else {
                    map.put("has_relation", false);
                }
                return ResultModel.newSuccessModel(map);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("getMerchantUser,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    public ResultModel saveMerchantUser(UserMerchantParam param) {


        /** 校验用户openid合法性*/
        if (!oAuth2UserService.isOpenExists(param.getOpenId())) {
            return ResultModel.openIdInvalid();
        }
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setMerchantOpenId(param.getMerchantOpenId());
        merchantUser.setOpenId(param.getOpenId());
        merchantUser.setStatus(RelationEnum.FRIEND.getValue());
        merchantUser.setCreateTime(new Date());
        merchantUser.setUpdateTime(new Date());

        try {

            IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByUserId(param.getOpenId());
            if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_USER_NOT_EXIST);
            }
            IMResult<String> saveResult = MerchantAdmin.createMerchantUser(merchantUser);
            if (saveResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("saveMerchantUser,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    /**
     * 拉黑用户,解除拉黑 0解除拉黑 1拉黑
     */
    @Override
    public ResultModel updateMerchantUser(UserMerchantParam param) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setMerchantOpenId(param.getMerchantOpenId());
        merchantUser.setOpenId(param.getOpenId());
        merchantUser.setStatus(param.getStatus());
        merchantUser.setUpdateTime(new Date());
        try {
            IMResult<String> updateResult = MerchantAdmin.updateMerchantUser(merchantUser);
            if (updateResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel();
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("updateMerchantUser,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    public ResultModel getUserInfo(String openId) {
        //昵称 openid 头像
        if (!oAuth2UserService.isOpenExists(openId)) {
            return ResultModel.openIdInvalid();
        }
        try {
            IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByUserId(openId);
            if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                InputOutputUserInfo result = userResult.getResult();
                UserInfoDto userInfo = new UserInfoDto();
                userInfo.setOpenId(result.getUserId());
                userInfo.setHeadPic(result.getPortrait());
                userInfo.setNickName(result.getDisplayName());
                return ResultModel.newSuccessModel(userInfo);
            } else {
                return ResultModel.parameterAbnormality();
            }
        } catch (Exception e) {
            logger.error("getUserInfo,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


}
