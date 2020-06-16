package com.jdd.imadmin.common.enums;

/**
 * @author @sailength on 2020/3/26.
 * 推送类型
 */
public enum PushTypeEnum {

    SINGLE(2, "单推"),
    MULTICAST(1, "群推");

    private int value;
    private String msg;

    PushTypeEnum(int value, String msg) {
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
