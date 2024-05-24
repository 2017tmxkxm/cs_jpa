package com.mysite.csJpa.question;


import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.user.SiteUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;

    @Builder
    public Question(int id, String content, String subject, LocalDateTime createDate, List<Answer> answerList, SiteUser author, LocalDateTime modifyDate, Set<SiteUser> voter) {
        this.id = id;
        this.content = content;
        this.subject = subject;
        this.createDate = createDate;
        this.answerList = answerList;
        this.author = author;
        this.modifyDate = modifyDate;
        this.voter = voter;
    }

}
