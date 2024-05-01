package com.mysite.csJpa.question;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.dto.AddQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * 전체 질문 List
     * @return List<Question>
     */
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

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
    
}

