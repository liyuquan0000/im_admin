package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


@Data
public class MerchantParam implements Serializable {

    /**
     * token
     */
    @NotEmpty(message="token 不能为空!")
    private String token;


    /**
     * 商户在技术服务商的唯一ID
     */
    private String merchantId;

    /**
     * 技术服务商appid
     */
    private String appid;

    /**
     * 商户名称
     */
    @NotEmpty(message="merchantName 不能为空!")
    private String merchantName;

    /**
     * 商户简称
     */
    @NotEmpty(message="shortName 不能为空!")
    private String shortName;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户头像
     */
    private String userHeadUrl;

    /**
     * 法人或者负责人的姓名
     */
    @NotEmpty(message="headName 不能为空!")
    private String headName;

    /**
     * 身份证号
     */
    private String headIdCard;

    /**
     * 身份证正面照片
     */
    private String headCardFrontPic;

    /**
     * 身份证反面照片
     */
    private String headCardReversePic;

    /**
     * 执照url
     */
    private String licenceUrl;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 介绍
     */
    private String description;

    /**
     * 介绍图片
     */
    private List<String> descriptionUrl;

    /**
     * 0：未审核，1：审核中，2：已开通
     */
    private Integer openStatus;

    /**
     * 状态   1可用 2冻结
     */
    private Integer status;




}