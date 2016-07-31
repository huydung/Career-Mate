package com.careermate.MODELS;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class SkillObject {
    private String key,name,icon;

    public SkillObject() {
    }

    public SkillObject(String key, String name, String icon) {
        this.key = key;
        this.name = name;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
