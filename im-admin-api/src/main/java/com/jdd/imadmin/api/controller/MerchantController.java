package com.jdd.imadmin.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.MerchantParam;
import com.jdd.imadmin.api.controller.param.UserMerchantParam;
import com.jdd.imadmin.api.service.MerchantService;
import com.jdd.imadmin.api.service.TokenVerifyService;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/public/merchant", produces = "application/json;charset=UTF-8")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private TokenVerifyService verifyService;

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    /**
     * 新建商户
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/saveMerchant")
    public ResultModel saveMerchant(@RequestBody @Valid MerchantParam param) {
        Oauth2Token oauth2Token = verifyService.getTokenDetail(param.getToken());
        if (oauth2Token == null) {
            return ResultModel.accessTokenInvalid();
        }

        param.setAppid(oauth2Token.getClientId());
        ResultModel resultModel = null;
        try {
            resultModel = merchantService.saveMerchant(param);
        } catch (Exception e) {
            logger.error("saveMerchant,异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
        }
        return resultModel;
    }


    /**
     * 修改商户
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/updateMerchant")
    public ResultModel updateMerchant(@RequestBody @Valid MerchantParam param) {

        Oauth2Token oauth2Token = verifyService.getTokenDetail(param.getToken());
        if (oauth2Token == null) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getMerchantId())) {
            return ResultModel.parameterNull("商户id不能为空");
        }
        param.setAppid(oauth2Token.getClientId());

        ResultModel resultModel = null;
        try {
            resultModel = merchantService.updateMerchant(param);
        } catch (Exception e) {
            logger.error("updateMerchant,异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
        }
        return resultModel;
    }


    /**
     * 获取商户详情
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/getMerchant")
    public ResultModel getMerchant(@RequestBody @Valid MerchantParam param) {

        Oauth2Token oauth2Token = verifyService.getTokenDetail(param.getToken());
        if (oauth2Token == null) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getMerchantId())) {
            return ResultModel.parameterNull("商户id不能为空");
        }

        ResultModel resultModel = null;
        param.setAppid(oauth2Token.getClientId());
        try {
            resultModel = merchantService.getMerchant(param);
        } catch (Exception e) {
            logger.error("getMerchant,异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
        }
        return resultModel;
    }


    /**
     * 获取用户和商户关系
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/getMerchantUserRelation")
    public ResultModel getMerchantUser(@RequestBody @Valid UserMerchantParam param) {

        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getOpenId()) || StringUtils.isBlank(param.getMerchantOpenId())) {
            return ResultModel.parameterNull("商户open_id或用户open_id不能为空");
        }
        ResultModel resultModel = null;
        try {
            resultModel = merchantService.getMerchantUser(param);
        } catch (Exception e) {
            logger.error("getMerchantUserRelation,异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
        }
        return resultModel;
    }


    /**
     * 创建商户与用户关系
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/saveMerchantUser")
    public ResultModel saveMerchantUser(@RequestBody @Valid UserMerchantParam param) {

        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getOpenId()) || StringUtils.isBlank(param.getMerchantOpenId())) {
            return ResultModel.parameterNull("商户open_id或用户open_id不能为空");
        }
        ResultModel resultModel = null;
        try {
            resultModel = merchantService.saveMerchantUser(param);
        } catch (Exception e) {
            logger.error("saveMerchantUser,异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
        }
        return resultModel;
    }


    /**
     * 商户拉黑用户
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/updateMerchantUser")
    public ResultModel updateMerchantUser(@RequestBody @Valid UserMerchantParam param) {

        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getOpenId()) || StringUtils.isBlank(param.getMerchantOpenId())) {
            return ResultModel.parameterNull("商户open_id或用户open_id不能为空");
        }
        ResultModel resultModel = null;
        try {
            resultModel = merchantService.updateMerchantUser(param);
        } catch (Exception e) {
            logger.error("updateMerchantUser,异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
        }
        return resultModel;
    }


    /**
     * 商户获取用户信息
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/getUserInfo")
    public ResultModel getUserInfo(@RequestBody @Valid UserMerchantParam param) {


        boolean verify = verifyService.verify(param.getToken());
        if (!verify) {
            return ResultModel.accessTokenInvalid();
        }

        if (StringUtils.isBlank(param.getOpenId())) {
            return ResultModel.parameterNull("用户open_id不能为空");
        }

        ResultModel resultModel = null;
        try {
            resultModel = merchantService.getUserInfo(param.getOpenId());
        } catch (Exception e) {
            logger.error("getUserInfo,异常={}，参数={}", ExceptionUtils.getStackTrace(e), JSONObject.toJSONString(param));
        }
        return resultModel;
    }


}
