package io.xujiaji.hnbc.model.entity;

/**
 * Created by jiana on 16-11-7.
 */

public class PopupItem {
    private String name;
    private int id;
    private int imgRes;

    public PopupItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public PopupItem(String name, int id, int imgRes) {
        this.name = name;
        this.id = id;
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
