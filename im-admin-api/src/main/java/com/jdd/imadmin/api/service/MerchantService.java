package com.jdd.imadmin.api.service;


import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.MerchantParam;
import com.jdd.imadmin.api.controller.param.UserMerchantParam;

public interface MerchantService {


    /**
     * 新增商户
     * @param param
     * @return
     */
    ResultModel saveMerchant(MerchantParam param);


    /**
     * 修改商户
     * @param param
     * @return
     */
    ResultModel updateMerchant(MerchantParam param);


    /**
     * 获取商户信息
     * @param param
     * @return
     */
    ResultModel getMerchant(MerchantParam param);


    /**
     * 获取用户和商户关系
     * @param param
     * @return
     */
    ResultModel getMerchantUser(UserMerchantParam param);



    /**
     * 创建商户与用户关系
     * @param param
     * @return
     */
    ResultModel saveMerchantUser(UserMerchantParam param);



    /**
     * 商户拉黑用户
     * @param param
     * @return
     */
    ResultModel updateMerchantUser(UserMerchantParam param);



    /**
     * 获取用户信息
     * @param openId
     * @return
     */
    ResultModel getUserInfo(String openId);


}
