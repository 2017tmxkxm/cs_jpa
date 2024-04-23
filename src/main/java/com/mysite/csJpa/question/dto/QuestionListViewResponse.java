package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.question.Question;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionListViewResponse {

    private final Integer id;
    private final String subject;
    private final String content;
    private final LocalDateTime createDate;

    public QuestionListViewResponse(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.createDate = question.getCreateDate();
    }
}
