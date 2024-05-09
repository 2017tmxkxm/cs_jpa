package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionListViewResponse {

    private final Integer id;
    private final String subject;
    private final String content;
    private final LocalDateTime createDate;
    private final List<Answer> answerList;

    public QuestionListViewResponse(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.createDate = question.getCreateDate();
        this.answerList = question.getAnswerList();
    }
}
