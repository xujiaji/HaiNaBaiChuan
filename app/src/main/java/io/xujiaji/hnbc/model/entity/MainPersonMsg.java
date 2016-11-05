package io.xujiaji.hnbc.model.entity;

/**
 * Created by jiana on 16-11-4.
 */

public class MainPersonMsg {
    private String name;
    private String headPic;
    private String bigPic;
    private String msgContent;
    private String sign;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getSign() {
        sign = sign == null ? "来自外星球的神秘人" : sign;
        return sign;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
