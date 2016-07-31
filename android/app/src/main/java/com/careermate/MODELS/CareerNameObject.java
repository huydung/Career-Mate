package com.careermate.MODELS;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class CareerNameObject {
    private String key,name;

    public CareerNameObject() {
    }

    public CareerNameObject(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
