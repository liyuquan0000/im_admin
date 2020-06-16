package com.jdd.imadmin.api.model;


import java.io.Serializable;

/**
 * @author @sailength on 2019/7/11.
 *         基础rpc返回类
 */
public class BaseRpcDTO<T> implements Serializable{

    private static final long serialVersionUID = -3322662975484964191L;
    public static final Integer SUCCESS = Integer.valueOf(0);
    public static final Integer ERROR = Integer.valueOf(-1);
    public static final Integer UN_LOGIN = Integer.valueOf(-2);
    public static final Integer ILLEGAL = Integer.valueOf(-3);
    public static final String MSG_SUCCESS_DESC = "操作成功！";
    public static final String MSG_ERROR_DESC = " 系统忙！";
    public static final String MSG_ERROR_SIGN = " 请求签名错误！";
    public static final String MSG_ILLEGAL_DESC = "参数不正确！";

    private int code;

    private String msg;

    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static BaseRpcDTO newSuccessModel(Object data) {
        BaseRpcDTO r = new BaseRpcDTO();
        r.setData(data);
        r.setMsg("操作成功！");
        r.setCode(SUCCESS);
        return r;
    }

    public static BaseRpcDTO newSuccessModel() {
        BaseRpcDTO r = new BaseRpcDTO();
        r.setMsg("操作成功！");
        r.setCode(SUCCESS);
        return r;
    }

    /**
     * 参数错误
     *
     * @param msg
     * @return
     */
    public static BaseRpcDTO newIllegalModel(String msg) {
        BaseRpcDTO r = new BaseRpcDTO();
        r.setMsg(msg);
        r.setCode(ILLEGAL);
        return r;
    }

    /**
     * 系统异常
     *
     * @param msg
     * @return
     */
    public static BaseRpcDTO newErrorModel(String msg) {
        BaseRpcDTO r = new BaseRpcDTO();
        r.setMsg(msg);
        r.setCode(ERROR);
        return r;
    }


    /**
     * 自定义异常返回类
     *
     * @return
     */
    public static BaseRpcDTO newError(Integer errorcOde, String msg) {
        BaseRpcDTO baseRpcDTO = new BaseRpcDTO();
        baseRpcDTO.setMsg(msg);
        baseRpcDTO.setCode(errorcOde);
        return baseRpcDTO;
    }


}
