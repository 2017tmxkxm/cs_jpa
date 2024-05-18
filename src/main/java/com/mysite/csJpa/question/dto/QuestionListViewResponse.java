package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
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
    private final SiteUser author;

    public QuestionListViewResponse(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.createDate = question.getCreateDate();
        this.answerList = question.getAnswerList();
        this.author = question.getAuthor();
    }
}
