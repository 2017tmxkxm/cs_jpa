package com.mysite.csJpa.question;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.dto.AddQuestionRequest;
import com.mysite.csJpa.question.dto.QuestionListViewResponse;
import com.mysite.csJpa.question.dto.QuestionViewResponse;
import com.mysite.csJpa.question.dto.UpdateQuestionRequest;
import com.mysite.csJpa.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * 전체 질문 List
     * @return List<Question>

    public List<Question> findAll() {
        return questionRepository.findAll();
    }
     */

    /**
     * 질문 상세
     *
     * @param id
     * @return Question
     */
    public Question findByOne(int id) {
        return questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("question not found"));
    }

    /**
     * 질문 저장
     * @param request - AddQuestionRequest DTO
     */
    public void save(AddQuestionRequest request, SiteUser siteUser) {
        questionRepository.save(request.toEntity(LocalDateTime.now(), siteUser));
    }

    /**
     * 전체 질문 List
     * @param page
     * @return Page<QuestionListViewResponse>
     */
    public Page<QuestionListViewResponse> findAll(int page, String kw) {
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(kw, pageable).map(QuestionListViewResponse::new);
    }

    /**
     * 질문 수정
     * @param question
     * @param subject
     * @param content
     */
    public void update(Question question, String subject, String content) {
        UpdateQuestionRequest updateQuestionRequest
                = UpdateQuestionRequest.builder()
                    .id(question.getId())
                    .subject(subject)
                    .content(content)
                    .author(question.getAuthor())
                    .createDate(question.getCreateDate())
                    .modifyDate(LocalDateTime.now())
                    .build();
        questionRepository.save(updateQuestionRequest.toEntity());
    }

    /**
     * 질문 삭제
     * @param id
     */
    public void delete(int id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("not found : " + id));
        questionRepository.delete(question);
    }

    /**
     * 질문 추천
     * @param question
     * @param siteUser
     */
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        UpdateQuestionRequest updateQuestionRequest
                = UpdateQuestionRequest.builder()
                    .id(question.getId())
                    .subject(question.getSubject())
                    .content(question.getContent())
                    .createDate(question.getCreateDate())
                    .modifyDate(question.getModifyDate())
                    .voter(question.getVoter())
                    .author(question.getAuthor())
                    .build();

        questionRepository.save(updateQuestionRequest.toEntity());
    }

}

