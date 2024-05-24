package com.mysite.csJpa.answer.dto;

import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class AnswerViewResponse {
    private Integer id;
    private String content;
    private LocalDateTime createDate;
    private Question question;
    private SiteUser author;
    private LocalDateTime modifyDate;
    Set<SiteUser> voter;
}
