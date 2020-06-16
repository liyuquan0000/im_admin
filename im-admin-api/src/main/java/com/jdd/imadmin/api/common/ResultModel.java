package com.jdd.imadmin.api.common;


import com.jdd.imadmin.common.enums.ApiErrorCode;

import java.io.Serializable;

public class ResultModel<T> implements Serializable {

    private int code = 0;
    private String msg = "";
    private T data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> ResultModel<T> newSuccessModel(T data) {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.SUCCESS.code);
        r.setData(data);
        r.setMsg(ApiErrorCode.SUCCESS.msg);
        return r;
    }

    public static <T> ResultModel<T> newSuccessModel() {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.SUCCESS.code);
        r.setMsg(ApiErrorCode.SUCCESS.msg);
        return r;
    }

    public static <T> ResultModel<T> newErrorModel(){
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR.code);
        r.setMsg(ApiErrorCode.ERROR.msg);
        return r;
    }

    public static <T> ResultModel<T> newErrorModel(String msg){
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR.code);
        r.setMsg(msg);
        return r;
    }

    public static <T> ResultModel<T> newErrorModel(T data){
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR.code);
        r.setData(data);
        return r;
    }

    public static <T> ResultModel<T> newErrorModel(ApiErrorCode errorCode){
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(errorCode.code);
        r.setMsg(errorCode.msg);
        return r;
    }

    public static <T> ResultModel<T> newErrorModel(ApiErrorCode errorCode, String msg){
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(errorCode.code);
        r.setMsg(msg);
        return r;
    }

    public static <T> ResultModel<T> accessTokenInvalid() {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR_CODE_ACCESS_TOKEN_INVALID.code);
        r.setMsg(ApiErrorCode.ERROR_CODE_ACCESS_TOKEN_INVALID.msg);
        return r;
    }

    public static <T> ResultModel<T> parameterNull(String msg){
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR_CODE_PARAMETER_NULL.code);
        r.setMsg(msg);
        return r;
    }

    public static <T> ResultModel<T> refreshTokenInvalid() {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR_CODE_REFRESH_TOKEN_INVALID.code);
        r.setMsg(ApiErrorCode.ERROR_CODE_REFRESH_TOKEN_INVALID.msg);
        return r;
    }

    public static <T> ResultModel<T> openIdInvalid() {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR_CODE_OPENID_INVALID.code);
        r.setMsg(ApiErrorCode.ERROR_CODE_OPENID_INVALID.msg);
        return r;
    }

    public static <T> ResultModel<T> timeOut() {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR_CODE_TIME_OUT.code);
        r.setMsg(ApiErrorCode.ERROR_CODE_TIME_OUT.msg);
        return r;
    }

    public static <T> ResultModel<T> parameterAbnormality() {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR_CODE_PARAMETER_ABNORMALITY.code);
        r.setMsg(ApiErrorCode.ERROR_CODE_PARAMETER_ABNORMALITY.msg);
        return r;
    }

    public static <T> ResultModel<T> parameterAbnormality(String msg) {
        ResultModel<T> r = new ResultModel<T>();
        r.setCode(ApiErrorCode.ERROR_CODE_PARAMETER_ABNORMALITY.code);
        r.setMsg(msg);
        return r;
    }
}
