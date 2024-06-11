package com.mysite.csJpa.comment;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private SiteUser author;

    @ManyToOne
    private Answer answer;

    @ManyToOne
    private Question question;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Builder
    public Comment(Integer id, String content, SiteUser author, Answer answer, LocalDateTime createDate, LocalDateTime modifyDate, Question question) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.answer = answer;
        this.question = question;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
