package com.mysite.csJpa.answer;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.answer.dto.*;
import com.mysite.csJpa.user.SiteUser;
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
    public AnswerResponse save(AnswerRequest request) {
        Answer saved = answerRepository.save(request.toEntityWithoutId());
        return AnswerResponse.builder()
                .content(saved.getContent())
                .createDate(saved.getCreateDate())
                .question(saved.getQuestion())
                .author(saved.getAuthor())
                .id(saved.getId())
                .build();
    }

    /**
     * 답변 조회
     * @param id
     * @return AnswerViewResponse
     */
    public AnswerResponse findByOne(int id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("answer not found"));
        return AnswerResponse.builder()
                .content(answer.getContent())
                .id(answer.getId())
                .author(answer.getAuthor())
                .question(answer.getQuestion())
                .createDate(answer.getCreateDate())
                .voter(answer.getVoter())
                .build();
    }

    /**
     * 답변 수정
     * @param response
     * @param request
     */
    public void answerUpdate(AnswerResponse response, AnswerRequest request) {
        request = AnswerRequest.builder()
                .id(response.getId())
                .content(request.getContent())
                .author(response.getAuthor())
                .question(response.getQuestion())
                .createDate(response.getCreateDate())
                .modifyDate(LocalDateTime.now())
                .build();
        answerRepository.save(request.toEntityWithId());
    }

    /**
     * 답변 삭제
     * @param id
     */
    public void answerDelete(int id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("not found : " + id));
        answerRepository.delete(answer);
    }

    /**
     * 답변 추천
     * @param request
     * @param siteUser
     */
    public void answerVote(AnswerResponse request, SiteUser siteUser) {
        request.getVoter().add(siteUser);
        AnswerRequest answerRequest
                = AnswerRequest.builder()
                    .id(request.getId())
                    .content(request.getContent())
                    .createDate(request.getCreateDate())
                    .modifyDate(request.getModifyDate())
                    .author(request.getAuthor())
                    .voter(request.getVoter())
                    .question(request.getQuestion())
                    .build();

        answerRepository.save(answerRequest.toEntityWithId());
    }
}
