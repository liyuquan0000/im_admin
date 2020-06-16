package com.jdd.imadmin.common.enums;


public enum RelationEnum {

    FRIEND(0, "好友"),
    DEFRIEND(1, "拉黑");

    private int value;
    private String msg;

    RelationEnum(int value, String msg) {
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
