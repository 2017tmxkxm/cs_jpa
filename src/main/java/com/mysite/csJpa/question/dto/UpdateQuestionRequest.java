package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class UpdateQuestionRequest {
    private String subject;
    private String content;
    private LocalDateTime modifyDate;
    private Integer id;
    private LocalDateTime createDate;
    private SiteUser author;
    private Set<SiteUser> voter;

    public Question toEntity() {
        return Question.builder()
                .id(id)
                .subject(subject)
                .content(content)
                .author(author)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .voter(voter)
                .build();
    }
}
