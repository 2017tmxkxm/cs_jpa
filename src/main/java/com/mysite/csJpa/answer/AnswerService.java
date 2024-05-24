package com.mysite.csJpa.answer;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.answer.dto.AnswerViewResponse;
import com.mysite.csJpa.answer.dto.UpdateAnswerRequest;
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
                .voter(answer.getVoter())
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
    public void answerVote(AnswerViewResponse request, SiteUser siteUser) {
        request.getVoter().add(siteUser);
        UpdateAnswerRequest updateAnswerRequest
                = UpdateAnswerRequest.builder()
                    .id(request.getId())
                    .content(request.getContent())
                    .createDate(request.getCreateDate())
                    .modifyDate(request.getModifyDate())
                    .author(request.getAuthor())
                    .voter(request.getVoter())
                    .question(request.getQuestion())
                    .build();

        answerRepository.save(updateAnswerRequest.toEntity());
    }
}
