package com.pluto.mongodb.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Description:
 * Author: Administrator
 * Date:2018-03-31 下午 05:26
 */
@Document(collection = "comments")
public class Comments {

    private List<Comment> lists;

    public List<Comment> getLists() {
        return lists;
    }

    public void setLists(List<Comment> lists) {
        this.lists = lists;
    }
}
