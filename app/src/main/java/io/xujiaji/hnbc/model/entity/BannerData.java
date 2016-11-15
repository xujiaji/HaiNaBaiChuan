package io.xujiaji.hnbc.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiana on 16-11-15.
 */

public class BannerData extends BmobObject{
    private String picUrl;
    private String title;
    private String linkTo;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }
}
