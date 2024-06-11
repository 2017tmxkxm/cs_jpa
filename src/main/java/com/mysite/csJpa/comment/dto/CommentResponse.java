package com.mysite.csJpa.comment.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.comment.Comment;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Integer id;

    private String content;

    private SiteUser author;

    private Answer answer;

    private Question question;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.answer = comment.getAnswer();
        this.question = comment.getQuestion();
        this.createDate = comment.getCreateDate();
        this.modifyDate = comment.getModifyDate();
    }
}
