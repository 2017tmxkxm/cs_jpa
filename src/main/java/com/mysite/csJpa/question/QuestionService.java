package com.mysite.csJpa.question;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.category.Category;
import com.mysite.csJpa.category.dto.CategoryResponse;
import com.mysite.csJpa.common.paging.RequestList;
import com.mysite.csJpa.common.paging.SearchDto;
import com.mysite.csJpa.question.dto.*;
import com.mysite.csJpa.question.mapper.QuestionMapper;
import com.mysite.csJpa.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    /**
     * 질문 상세
     * @param id
     * @return Question
     */
    public QuestionResponse findByOne(int id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("question not found"));

        return QuestionResponse.builder()
                .id(question.getId())
                .subject(question.getSubject())
                .content(question.getContent())
                .answerList(question.getAnswerList())
                .author(question.getAuthor())
                .createDate(question.getCreateDate())
                .modifyDate(question.getModifyDate())
                .voter(question.getVoter())
                .categoryId(question.getCategory().getId())
                .build();
    }

    /**
     * 질문 저장
     * @param request - AddQuestionRequest DTO
     */
    public void save(QuestionRequest request, SiteUser siteUser, int categoryId) {
        // answerService의 save는 builder를 사용해서 AnswerResponse return
        // QuestionService의 save는 void
        CategoryResponse categoryResponse = new CategoryResponse();
        Category category = categoryResponse.toEntity(categoryId);

        questionRepository.save(request.toEntityForAdd(LocalDateTime.now(), siteUser, category));
    }

    /**
     * 전체 질문 List
     * @param page
     * @return Page<QuestionResponse>

    public Page<QuestionResponse> findAll(int page, String kw, int categoryId) {
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(kw, categoryId, pageable).map(QuestionResponse::new);
    }
     */

    /**
     * 질문 수정
     * @param question
     * @param subject
     * @param content
     */
    public void update(QuestionResponse question, String subject, String content, int categoryId) {
        CategoryResponse categoryResponse = new CategoryResponse();
        Category category = categoryResponse.toEntity(categoryId);

        QuestionRequest questionRequest
                = QuestionRequest.builder()
                    .id(question.getId())
                    .subject(subject)
                    .content(content)
                    .author(question.getAuthor())
                    .category(category)
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

    /**
     * 전체 목록
     * @param params
     * @param pageable
     * @return
     */
    public Page<QuestionResponse> findAll(SearchDto params, Pageable pageable) {

        RequestList<?> requestList = RequestList.builder()
                .pageable(pageable)
                .data(params)
                .build();

        List<QuestionResponse> content = questionMapper.findAll(requestList);

        int total = questionMapper.getQuestionCount(params);

        return new PageImpl<>(content, pageable, total);
    }

}

