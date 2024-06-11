package com.mysite.csJpa.comment.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.comment.Comment;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentRequest {
    private Integer id;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

    private SiteUser author;

    private Answer answer;

    private Question question;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public Comment toEntity() {
        return Comment.builder()
                .id(id)
                .content(content)
                .author(author)
                .answer(answer)
                .question(question)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .build();
    }
}
