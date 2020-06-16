package com.jdd.imadmin.common.enums;

/**
 * @author @sailength on 2020/3/26.
 */
public enum HrefTypeEnum {


    HREF(1, "超链接"),

    NON_HREF(0, "文字");


    private int value;
    private String msg;

    HrefTypeEnum(int value, String msg) {
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
