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

    // 생성자 생성후 Question question 받은 후 get() 처리를 해줘야 한다.
    public QuestionListViewResponse(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.createDate = question.getCreateDate();
    }
}
