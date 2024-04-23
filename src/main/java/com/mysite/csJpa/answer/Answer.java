package com.mysite.csJpa.answer;

import com.mysite.csJpa.question.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Builder
    public Answer(String content, Question question, LocalDateTime createDate) {
        this.content = content;
        this.question = question;
        this.createDate = createDate;
    }
}
