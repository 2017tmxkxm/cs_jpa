package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionViewResponse {

    private final int id;
    private final String subject;
    private final String content;
    private final List<Answer> answerList;
    private final LocalDateTime createDate;
    private final SiteUser author;
    private final LocalDateTime modifyDate;

    public QuestionViewResponse(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.answerList = question.getAnswerList();
        this.createDate = question.getCreateDate();
        this.author = question.getAuthor();
        this.modifyDate = question.getModifyDate();
    }
}
