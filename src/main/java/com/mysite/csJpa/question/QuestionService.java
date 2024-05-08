package com.mysite.csJpa.question;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.question.dto.AddQuestionRequest;
import com.mysite.csJpa.question.dto.QuestionListViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
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
    public void save(AddQuestionRequest request) {
        questionRepository.save(request.toEntity(LocalDateTime.now()));
    }

    /**
     * 전체 질문 List
     * @param page
     * @return Page<QuestionListViewResponse>
     */
    public Page<QuestionListViewResponse> findAll(int page) {
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable).map(QuestionListViewResponse::new);
    }

}

