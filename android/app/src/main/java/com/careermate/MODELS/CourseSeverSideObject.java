package com.careermate.MODELS;

import java.util.List;

/**
 * Created by TuAnh on 7/31/2016.
 */
public class CourseSeverSideObject {
    private String name,key;
    private long estimated;
    private String thumbnail;
    private List<SkillNameObject> skills;
    private List<LessonObject> lessons;

    public CourseSeverSideObject() {
    }

    public CourseSeverSideObject(String name, String key, long estimated, String thumbnail, List<SkillNameObject> skills, List<LessonObject> lessons) {
        this.name = name;
        this.key = key;
        this.estimated = estimated;
        this.thumbnail = thumbnail;
        this.skills = skills;
        this.lessons = lessons;
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

    public long getEstimated() {
        return estimated;
    }

    public void setEstimated(long estimated) {
        this.estimated = estimated;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<SkillNameObject> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillNameObject> skills) {
        this.skills = skills;
    }

    public List<LessonObject> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonObject> lessons) {
        this.lessons = lessons;
    }
}
