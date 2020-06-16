package com.jdd.imadmin.api.controller.param;

import cn.wildfirechat.pojos.MessagePayload;

import java.util.List;

public class BaseParam {

    private List<Integer> toLines;
    private MessagePayload notifyMessage;

    public List<Integer> getToLines() {
        return toLines;
    }

    public void setToLines(List<Integer> toLines) {
        this.toLines = toLines;
    }

    public MessagePayload getNotifyMessage() {
        return notifyMessage;
    }

    public void setNotifyMessage(MessagePayload notifyMessage) {
        this.notifyMessage = notifyMessage;
    }
}
