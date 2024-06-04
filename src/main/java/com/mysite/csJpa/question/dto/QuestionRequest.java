package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class QuestionRequest {

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max=200)
    private String subject;
    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
    private LocalDateTime modifyDate;
    private Integer id;
    private LocalDateTime createDate;
    private SiteUser author;
    private Set<SiteUser> voter;

    public Question toEntityForUpdate() {
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

    public Question toEntityForAdd(LocalDateTime createDate, SiteUser author) {
        return Question.builder()
                .subject(subject)
                .content(content)
                .createDate(createDate)
                .author(author)
                .build();
    }
}
