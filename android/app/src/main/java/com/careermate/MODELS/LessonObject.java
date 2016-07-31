package com.careermate.MODELS;

import java.util.List;

/**
 * Created by TuAnh on 7/31/2016.
 */
public class LessonObject {
    private List<CardServerSideObject> cards;
    private String name;

    public LessonObject(List<CardServerSideObject> cards, String name) {
        this.cards = cards;
        this.name = name;
    }

    public LessonObject() {
    }

    public List<CardServerSideObject> getCards() {
        return cards;
    }

    public void setCards(List<CardServerSideObject> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
