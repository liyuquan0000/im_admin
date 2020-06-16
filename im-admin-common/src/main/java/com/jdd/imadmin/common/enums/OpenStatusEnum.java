package com.jdd.imadmin.common.enums;



public enum OpenStatusEnum {

    UNAUDITED(0, "未审核"),
    NOPASS(1, "审核不过"),
    PASS(2, "已开通");

    private int value;
    private String msg;

    OpenStatusEnum(int value, String msg) {
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
