package com.teach.bean;

public class EduQuestionsTagRelation {
    private Integer id;

    private Integer questionsId;

    private Integer questionsTagId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(Integer questionsId) {
        this.questionsId = questionsId;
    }

    public Integer getQuestionsTagId() {
        return questionsTagId;
    }

    public void setQuestionsTagId(Integer questionsTagId) {
        this.questionsTagId = questionsTagId;
    }
}