package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.question.Question;
import lombok.Getter;

@Getter
public class QuestionViewResponse {

    private final String subject;
    private final String content;

    public QuestionViewResponse(Question question) {
        this.subject = question.getSubject();
        this.content = question.getContent();
    }
}
