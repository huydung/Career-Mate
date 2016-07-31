package com.careermate.MODELS;

import java.util.List;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class UserObject {
    private String fbId,messengerId,username,firebaseId;
    private String expected_answer,milestone;
    private List<CareerNameObject> careers;
    private List<SkillNameObject> skills;
    private List<CourseItemObject> courses;

    public UserObject() {
    }

    public UserObject(String fbId, String messengerId, String username, String firebaseId, String expected_answer, String milestone, List<CareerNameObject> careers, List<SkillNameObject> skills, List<CourseItemObject> courses) {
        this.fbId = fbId;
        this.messengerId = messengerId;
        this.username = username;
        this.firebaseId = firebaseId;
        this.expected_answer = expected_answer;
        this.milestone = milestone;
        this.careers = careers;
        this.skills = skills;
        this.courses = courses;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }


    public String getExpected_answer() {
        return expected_answer;
    }

    public void setExpected_answer(String expected_answer) {
        this.expected_answer = expected_answer;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(String messengerId) {
        this.messengerId = messengerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CareerNameObject> getCareers() {
        return careers;
    }

    public void setCareers(List<CareerNameObject> careers) {
        this.careers = careers;
    }

    public List<SkillNameObject> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillNameObject> skills) {
        this.skills = skills;
    }

    public List<CourseItemObject> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseItemObject> courses) {
        this.courses = courses;
    }
}
