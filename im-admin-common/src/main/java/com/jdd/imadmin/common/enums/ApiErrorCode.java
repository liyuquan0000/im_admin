package com.jdd.imadmin.common.enums;

public enum ApiErrorCode {

    // 全局异常
    SUCCESS(0, "成功"),
    ERROR(-1, "系统异常"),
    ERROR_CODE_ACCESS_TOKEN_INVALID(100001, "访问令牌无效"),
    ERROR_CODE_REFRESH_TOKEN_INVALID(100002, "刷新令牌无效"),
    ERROR_CODE_OPENID_INVALID(100003, "openId 无效"),
    ERROR_CODE_PARAMETER_NULL(100004, "参数为空"),
    ERROR_CODE_PARAMETER_ABNORMALITY(100005, "参数异常"),
    ERROR_CODE_TIME_OUT(100006, "请求超时"),

    // 服务号
    ERROR_CODE_KEYWORD_REPETITION(101001, "关键词重复"),
    ERROR_CODE_TEMPLATE_NOT_FOUND(101002, "模板未找到"),
    ERROR_CODE_TEMPLATE_NOT_MATCH(101003, "模板不匹配"),
    ERROR_CODE_CLIENT_NOT_FOUND(101004, "client 没找到"),
    ERROR_CODE_SERVICE_ACCOUNT_NAME_ALREADY_EXIST(101005, "已经存在相同名称的服务号，创建失败"),


    // 群
    ERROR_CODE_GROUP_ALREADY_EXIST(102001, "群已存在"),
    ERROR_CODE_GROUP_COUNT_CAP(102002, "群成员上限"),
    ERROR_CODE_GROUP_MANAGER_CAP(102003, "群管理上限"),
    ERROR_CODE_NOT_GROUP_MEMBER(102004, "不是群成员"),
    ERROR_CODE_GROUP_APPLY_NOT_EXIST(102005, "申请入群记录不存在"),
    ERROR_CODE_APPROVED(102006, "已经提交过审批"),
    ERROR_CODE_APPROVE_EXPIRED(102007, "申请已过期"),

    // 商户
    ERROR_CODE_MERCHANT_MEMBER(103001, "商户用户创建失败"),
    ERROR_CODE_MERCHANT_ALREADY_EXIST(103002, "商户已存在"),
    ERROR_CODE_MERCHANT_NOT_EXIST(103003, "商户不存在"),
    ERROR_CODE_USER_NOT_EXIST(103004, "用户不存在"),
    //版本
    ERROR_CODE_NONE_VERSION(104001,"没有版本数据");



    public int code;
    public String msg;

    ApiErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiErrorCode fromCode(int code) {
        for (ApiErrorCode apiErrorCode : ApiErrorCode.values()) {
            if(apiErrorCode.code == (code)) {
                return apiErrorCode;
            }
        }
        return null;
    }
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
