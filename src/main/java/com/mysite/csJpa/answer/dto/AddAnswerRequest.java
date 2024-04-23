package com.mysite.csJpa.answer.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAnswerRequest {

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
