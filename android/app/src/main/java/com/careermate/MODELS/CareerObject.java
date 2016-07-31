package com.careermate.MODELS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class CareerObject {
    private String icon,key,name;
    private List<SkillNameObject> skills= new ArrayList<>();

    public CareerObject() {
    }

    public CareerObject(String icon, String key, String name, List<SkillNameObject> skillNameObjectList) {
        this.icon = icon;
        this.key = key;
        this.name = name;
        this.skills = skillNameObjectList;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
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

    public List<SkillNameObject> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillNameObject> skills) {
        this.skills = skills;
    }
}
