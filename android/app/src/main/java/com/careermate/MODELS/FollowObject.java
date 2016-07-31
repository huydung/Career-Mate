package com.careermate.MODELS;

import java.util.List;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class FollowObject {
    private String name;
    private List<FacebookUriObject> list;

    public FollowObject(String name, List<FacebookUriObject> list) {
        this.name = name;
        this.list = list;
    }

    public FollowObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FacebookUriObject> getList() {
        return list;
    }

    public void setList(List<FacebookUriObject> list) {
        this.list = list;
    }
}
