package com.mysite.csJpa.answer;

import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.answer.dto.AnswerViewResponse;
import com.mysite.csJpa.answer.dto.UpdateAnswerRequest;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.question.QuestionService;
import com.mysite.csJpa.user.SiteUser;
import com.mysite.csJpa.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/create/{id}")
    public String createAnswer(@PathVariable("id") int questionId
            , @Valid @ModelAttribute(value = "answer") AddAnswerRequest answer
            , BindingResult bindingResult
            , Model model
            , Principal principal) {

        Question question = questionService.findByOne(questionId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .content(answer.getContent())
                .question(question)
                .createDate(LocalDateTime.now())
                .author(userService.find(principal.getName()))
                .build();
        answerService.save(addAnswerRequest);
        return String.format("redirect:/question/detail/%s", questionId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/update/{id}")
    public String answerUpdate(@ModelAttribute("answerForm") AnswerViewResponse request, @PathVariable("id") int id, Principal principal, Model model) {
        AnswerViewResponse answer = answerService.findByOne(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        request = AnswerViewResponse.builder()
                .content(answer.getContent())
                .build();
        model.addAttribute("answerForm", request);
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/update/{id}")
    public String answerUpdate(@Valid @ModelAttribute("answerForm") UpdateAnswerRequest request, BindingResult bindingResult
            , @PathVariable("id") int id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }

        AnswerViewResponse answer = answerService.findByOne(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        answerService.answerUpdate(answer, request);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/delete/{id}")
    public String deleteAnswer(@PathVariable(value = "id") Integer id, Principal principal) {
        AnswerViewResponse answerViewResponse = answerService.findByOne(id);
        if (!answerViewResponse.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        answerService.answerDelete(id);
        return String.format("redirect:/question/detail/%s", answerViewResponse.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        AnswerViewResponse answerViewResponse = answerService.findByOne(id);
        SiteUser siteUser = userService.find(principal.getName());
        answerService.answerVote(answerViewResponse, siteUser);
        return String.format("redirect:/question/detail/%s", answerViewResponse.getQuestion().getId());
    }
}
