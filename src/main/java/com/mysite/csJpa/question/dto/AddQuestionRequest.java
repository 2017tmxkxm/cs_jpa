package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddQuestionRequest {

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max=200)
    private String subject;
    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private SiteUser author;

    public Question toEntity(LocalDateTime createDate, SiteUser author) {
        return Question.builder()
                .subject(subject)
                .content(content)
                .createDate(createDate)
                .author(author)
                .build();
    }
}
