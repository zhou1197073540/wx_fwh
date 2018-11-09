package com.wxfwh.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class DecryptMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("Encrypt")
    private String encrypt;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }
}
