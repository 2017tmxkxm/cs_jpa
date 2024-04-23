package com.mysite.csJpa.answer;

import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    /**
     * 답변 저장
     * @param request
     */
    public void save(AddAnswerRequest request) {
        answerRepository.save(request.toEntity());
    }
}
