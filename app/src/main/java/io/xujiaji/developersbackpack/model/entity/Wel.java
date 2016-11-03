package io.xujiaji.developersbackpack.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiana on 16-7-24.
 */
public class Wel extends BmobObject {
    private String imgDate;
    private String imgAddress;

    public String getImgDate() {
        return imgDate;
    }

    public void setImgDate(String imgDate) {
        this.imgDate = imgDate;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    @Override
    public String toString() {
        return "Wel{" +
                "imgDate='" + imgDate + '\'' +
                ", imgAddress='" + imgAddress + '\'' +
                '}';
    }
}
