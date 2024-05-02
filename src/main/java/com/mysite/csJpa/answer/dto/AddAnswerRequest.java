package com.mysite.csJpa.answer.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AddAnswerRequest {

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private Question question;
    private LocalDateTime createDate;

    public Answer toEntity() {
        return Answer.builder()
                .content(content)
                .question(question)
                .createDate(createDate)
                .build();
    }
}
