package com.mysite.csJpa.answer.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class UpdateAnswerRequest {
    private Integer id;
    private String content;
    private LocalDateTime modifyDate;
    private Question question;
    private LocalDateTime createDate;
    private SiteUser author;
    Set<SiteUser> voter;

    public Answer toEntity() {
        return Answer.builder()
                .content(content)
                .modifyDate(modifyDate)
                .id(id)
                .author(author)
                .createDate(createDate)
                .question(question)
                .voter(voter)
                .build();
    }
}
