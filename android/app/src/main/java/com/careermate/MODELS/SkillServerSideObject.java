package com.careermate.MODELS;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class SkillServerSideObject {
    private String name,icon;

    public SkillServerSideObject() {
    }

    public SkillServerSideObject(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
