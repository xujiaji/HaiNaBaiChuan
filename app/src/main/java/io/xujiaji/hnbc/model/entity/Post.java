package io.xujiaji.hnbc.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by jiana on 16-11-13.
 * 帖子类
 */

public class Post extends BmobObject{
    /**
     *  帖子标题
     */
    private String title;

    /**
     *  帖子内容
     */
    private String content;

    /**
     * 封面图
     */
    private String coverPicture;

    /**
     *  发布者
     */
    private User author;

    /**
     * 喜欢该帖子的人
     */
    private BmobRelation likes;//多对多关系：用于存储喜欢该帖子的所有用户

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", author=" + author +
                ", likes=" + likes +
                '}';
    }
}
