package com.wxfwh.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class TextMessage extends BaseMessage {
    @XStreamAlias("Content")
    private String content;

    public TextMessage(BaseMessage base) {
        this.setToUserName(base.getToUserName());
        this.setFromUserName(base.getFromUserName());
        this.setCreateTime(base.getCreateTime());
        this.setMsgType(base.getMsgType());
    }
    public TextMessage() {
    }

    public String getContent() {
        return content;
    }

    public TextMessage setContent(String content) {
        this.content = content;
        return this;
    }

}
