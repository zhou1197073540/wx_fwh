package com.wxfwh.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ImageMessage extends BaseMessage {
    @XStreamAlias("Image")
    private ImageAttrMessage image;

    public ImageMessage() {
        this.image = new ImageAttrMessage();
    }
    public void setMediaId(String mediaId) {
        this.image.setMediaId(mediaId);
    }
}
class ImageAttrMessage {
    @XStreamAlias("MediaId")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}





