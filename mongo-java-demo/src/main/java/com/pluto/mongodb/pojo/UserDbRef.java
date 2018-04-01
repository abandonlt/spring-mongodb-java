package com.pluto.mongodb.pojo;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * Author: Administrator
 * Date:2018-03-31 下午 05:28
 */
public class UserDbRef {

    private ObjectId id;

    private String username;

    private Address address;

    private Favorites favorites;

    private int age;

    private BigDecimal salary;

    private float length;

    private List<Comment> comments;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
