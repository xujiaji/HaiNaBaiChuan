package io.xujiaji.hnbc.model.entity;

import java.util.Date;

import cn.bmob.v3.BmobUser;

/**
 * Created by jiana on 16-11-4.
 */

public class User extends BmobUser{
    private String nickname;
    private int age;
    private Date birthday;
    private int sex;
    private String sign;
    private String headPic;

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", sign='" + sign + '\'' +
                ", headPic='" + headPic + '\'' +
                '}' + super.toString();
    }
}
