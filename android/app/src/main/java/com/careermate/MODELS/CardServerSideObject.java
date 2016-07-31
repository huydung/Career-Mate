package com.careermate.MODELS;

/**
 * Created by TuAnh on 7/31/2016.
 */
public class CardServerSideObject {
    private String type,source_url,presentation,content,answer;
    private ThreeOptionsObject options;
    /*
    * Card type: quiz (freetext,generic) , text, video
    * */

    public CardServerSideObject() {
    }

    public CardServerSideObject(String type, String source_url, String presentation, String content, String answer, ThreeOptionsObject options) {
        this.type = type;
        this.source_url = source_url;
        this.presentation = presentation;
        this.content = content;
        this.answer = answer;
        this.options = options;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ThreeOptionsObject getOptions() {
        return options;
    }

    public void setOptions(ThreeOptionsObject options) {
        this.options = options;
    }
}
