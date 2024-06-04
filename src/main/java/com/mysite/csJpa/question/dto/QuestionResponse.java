package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private final Integer id;
    private final String subject;
    private final String content;
    private final List<Answer> answerList;
    private final SiteUser author;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    Set<SiteUser> voter;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.answerList = question.getAnswerList();
        this.createDate = question.getCreateDate();
        this.author = question.getAuthor();
        this.modifyDate = question.getModifyDate();
        this.voter = question.getVoter();
    }

    public Question toEntity() {
        return Question.builder()
                .id(id)
                .subject(subject)
                .content(content)
                .answerList(answerList)
                .author(author)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .voter(voter)
                .build();
    }
}
