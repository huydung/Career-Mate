package com.careermate.MODELS;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class CourseItemObject {
    private String key,name,status,next_lesson,next_card;

    public CourseItemObject(String key, String name, String status, String next_lesson, String next_card) {
        this.key = key;
        this.name = name;
        this.status = status;
        this.next_lesson = next_lesson;
        this.next_card = next_card;
    }

    public CourseItemObject() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNext_lesson() {
        return next_lesson;
    }

    public void setNext_lesson(String next_lesson) {
        this.next_lesson = next_lesson;
    }

    public String getNext_card() {
        return next_card;
    }

    public void setNext_card(String next_card) {
        this.next_card = next_card;
    }
}
