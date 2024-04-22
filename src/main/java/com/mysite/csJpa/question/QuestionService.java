package com.mysite.csJpa.question;

import com.mysite.csJpa.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * 전체 질문 List
     * @return List<Question>
     */
    public List<Question> getList() {
        return questionRepository.findAll();
    }

    /**
     * 질문 상세
     * @param id
     * @return Question
     */
    public Question getQuestion(int id) {
        return questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("question not found"));
    }
    
}

