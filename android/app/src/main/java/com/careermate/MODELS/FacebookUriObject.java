package com.careermate.MODELS;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class FacebookUriObject {
    private String fbUri,fbUrl,fbName;

    public FacebookUriObject(String fbUri, String fbUrl, String fbName) {
        this.fbUri = fbUri;
        this.fbUrl = fbUrl;
        this.fbName = fbName;
    }

    public FacebookUriObject() {
    }

    public String getFbUri() {
        return fbUri;
    }

    public void setFbUri(String fbUri) {
        this.fbUri = fbUri;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public void setFbUrl(String fbUrl) {
        this.fbUrl = fbUrl;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }
}
