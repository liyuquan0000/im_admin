package com.jdd.imadmin.common.enums;



public enum StatusEnum {

    VALID(1, "有效"),
    INVALID(2, "无效");

    private int value;
    private String msg;

    StatusEnum(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }


    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

}
