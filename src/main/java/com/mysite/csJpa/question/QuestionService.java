package com.mysite.csJpa.question;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.answer.Answer;
import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.dto.*;
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
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * 질문 상세
     * @param id
     * @return Question
     */
    public QuestionResponse findByOne(int id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("question not found"));

        return new QuestionResponse(
                question.getId(), question.getSubject(), question.getContent(), question.getAnswerList()
                , question.getAuthor(), question.getCreateDate(), question.getModifyDate(), question.getVoter());
    }

    /**
     * 질문 저장
     * @param request - AddQuestionRequest DTO
     */
    public void save(QuestionRequest request, SiteUser siteUser) {
        // answerService의 save는 builder를 사용해서 AnswerResponse return
        // QuestionService의 save는 void
        questionRepository.save(request.toEntityForAdd(LocalDateTime.now(), siteUser));
    }

    /**
     * 전체 질문 List
     * @param page
     * @return Page<QuestionResponse>
     */
    public Page<QuestionResponse> findAll(int page, String kw) {
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(kw, pageable).map(QuestionResponse::new);
    }

    /**
     * 질문 수정
     * @param question
     * @param subject
     * @param content
     */
    public void update(QuestionResponse question, String subject, String content) {
        QuestionRequest questionRequest
                = QuestionRequest.builder()
                    .id(question.getId())
                    .subject(subject)
                    .content(content)
                    .author(question.getAuthor())
                    .createDate(question.getCreateDate())
                    .modifyDate(LocalDateTime.now())
                    .build();
        questionRepository.save(questionRequest.toEntityForUpdate());
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
    public void vote(QuestionResponse question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        QuestionRequest questionRequest
                = QuestionRequest.builder()
                    .id(question.getId())
                    .subject(question.getSubject())
                    .content(question.getContent())
                    .createDate(question.getCreateDate())
                    .modifyDate(question.getModifyDate())
                    .voter(question.getVoter())
                    .author(question.getAuthor())
                    .build();

        questionRepository.save(questionRequest.toEntityForUpdate());
    }

}

