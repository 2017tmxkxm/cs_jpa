package com.mysite.csJpa.answer;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.answer.dto.AnswerViewResponse;
import com.mysite.csJpa.answer.dto.UpdateAnswerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    /**
     * 답변 조회
     * @param id
     * @return AnswerViewResponse
     */
    public AnswerViewResponse findByOne(int id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("answer not found"));
        return AnswerViewResponse.builder()
                .content(answer.getContent())
                .id(answer.getId())
                .author(answer.getAuthor())
                .question(answer.getQuestion())
                .createDate(answer.getCreateDate())
                .build();
    }

    /**
     * 답변 수정
     * @param answerViewResponse
     * @param updateAnswerRequest
     */
    public void answerUpdate(AnswerViewResponse answerViewResponse, UpdateAnswerRequest updateAnswerRequest) {
        updateAnswerRequest = UpdateAnswerRequest.builder()
                .id(answerViewResponse.getId())
                .content(updateAnswerRequest.getContent())
                .author(answerViewResponse.getAuthor())
                .question(answerViewResponse.getQuestion())
                .createDate(answerViewResponse.getCreateDate())
                .modifyDate(LocalDateTime.now())
                .build();
        answerRepository.save(updateAnswerRequest.toEntity());
    }

    public void answerDelete(int id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("not found : " + id));
        answerRepository.delete(answer);
    }
}
