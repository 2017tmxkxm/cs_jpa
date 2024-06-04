package com.mysite.csJpa.answer.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class AnswerRequest {

    private Integer id;
    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private Question question;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private SiteUser author;
    Set<SiteUser> voter;

    public Answer toEntityWithId() {
        return Answer.builder()
                .id(id)
                .content(content)
                .question(question)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .author(author)
                .voter(voter)
                .build();
    }

    public Answer toEntityWithoutId() {
        return Answer.builder()
                .content(content)
                .question(question)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .author(author)
                .voter(voter)
                .build();
    }

}
