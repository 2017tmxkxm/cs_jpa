package com.mysite.csJpa.question.dto;

import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.category.Category;
import com.mysite.csJpa.category.dto.CategoryResponse;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.user.SiteUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class QuestionResponse {
    private Integer id;
    private String subject;
    private String content;
    private List<Answer> answerList;
    private SiteUser author;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    Set<SiteUser> voter;
    private Category category;
    private Integer categoryId;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.answerList = question.getAnswerList();
        this.createDate = question.getCreateDate();
        this.author = question.getAuthor();
        this.modifyDate = question.getModifyDate();
        this.voter = question.getVoter();
        this.category = question.getCategory();
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
                .category(category)
                .build();
    }
}
