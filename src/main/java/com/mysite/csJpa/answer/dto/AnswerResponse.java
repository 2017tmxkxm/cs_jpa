package com.mysite.csJpa.answer.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class AnswerResponse {

    private Integer id;
    private String content;
    private Question question;
    private SiteUser author;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    Set<SiteUser> voter;

    public AnswerResponse(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.question = answer.getQuestion();
        this.author = answer.getAuthor();
        this.createDate = answer.getCreateDate();
        this.modifyDate = answer.getModifyDate();
        this.voter = answer.getVoter();
    }
}
