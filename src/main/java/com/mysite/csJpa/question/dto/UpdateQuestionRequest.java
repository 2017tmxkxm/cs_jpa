package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateQuestionRequest {
    private String subject;
    private String content;
    private LocalDateTime modifyDate;
    private Integer id;
    private LocalDateTime createDate;
    private SiteUser author;

    public Question toEntity() {
        return Question.builder()
                .subject(subject)
                .content(content)
                .modifyDate(modifyDate)
                .id(id)
                .createDate(createDate)
                .author(author)
                .build();
    }
}
