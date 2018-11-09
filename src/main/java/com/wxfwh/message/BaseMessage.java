package com.wxfwh.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BaseMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private long createTime;

    @XStreamAlias("MsgType")
    private String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public BaseMessage setToUserName(String toUserName) {
        this.toUserName = toUserName;
        return this;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public BaseMessage setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public BaseMessage setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getMsgType() {
        return msgType;
    }

    public BaseMessage setMsgType(String fsgType) {
        this.msgType = fsgType;
        return this;
    }
}
