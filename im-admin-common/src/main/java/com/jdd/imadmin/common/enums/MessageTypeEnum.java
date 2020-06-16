package com.jdd.imadmin.common.enums;

/**
 * @author @sailength on 2020/3/26.
 *         消息类型
 */
public enum MessageTypeEnum {

    TEXT(1, 10, "文字推送"),
    HREF_TEXT(2, 10, "超链接文字推送"),
    TEMPLATE(3, 11, "图文推送");

    private int value;
    private int innerType;
    private String msg;

    MessageTypeEnum(int value, int innerType, String msg) {
        this.innerType = innerType;
        this.value = value;
        this.msg = msg;
    }
    


    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public int getInnerType() {
        return innerType;
    }

    public void setInnerType(int innerType) {
        this.innerType = innerType;
    }
}
