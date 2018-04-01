package com.pluto.mongodb.pojo;

import java.util.Date;

/**
 * Description:
 * Author: Administrator
 * Date:2018-03-31 下午 05:23
 */
public class Comment {
    private String author;

    private Date commentTime;

    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
