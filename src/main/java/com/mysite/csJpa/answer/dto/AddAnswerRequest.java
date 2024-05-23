package com.mysite.csJpa.answer.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class AddAnswerRequest {

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private Question question;
    private LocalDateTime createDate;
    private SiteUser author;

    public Answer toEntity() {
        return Answer.builder()
                .content(content)
                .question(question)
                .createDate(createDate)
                .author(author)
                .build();
    }
}
