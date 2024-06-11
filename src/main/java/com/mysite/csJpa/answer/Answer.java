package com.mysite.csJpa.answer;

import com.mysite.csJpa.comment.Comment;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @Builder
    public Answer(String content, Question question, LocalDateTime createDate, SiteUser author, LocalDateTime modifyDate, int id, Set<SiteUser> voter, List<Comment> commentList) {
        this.content = content;
        this.question = question;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.id = id;
        this.author = author;
        this.voter = voter;
        this.commentList = commentList;
    }
}
