package com.mysite.csJpa.answer;

import com.mysite.csJpa.answer.dto.*;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.question.QuestionService;
import com.mysite.csJpa.question.dto.QuestionResponse;
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
            , @Valid @ModelAttribute(value = "answer") AnswerRequest request
            , BindingResult bindingResult
            , Model model
            , Principal principal) {

        QuestionResponse question = questionService.findByOne(questionId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        AnswerRequest addAnswerRequest = AnswerRequest.builder()
                .content(request.getContent())
                .createDate(LocalDateTime.now())
                .author(userService.find(principal.getName()))
                .build();

        AnswerResponse answerSaved = answerService.save(addAnswerRequest, question);

        return String.format("redirect:/question/detail/%s#answer_%s", questionId, answerSaved.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/update/{id}")
    public String answerUpdate(@ModelAttribute("answerForm") AnswerResponse request, @PathVariable("id") int id, Principal principal, Model model) {
        AnswerResponse answer = answerService.findByOne(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        request = AnswerResponse.builder()
                .content(answer.getContent())
                .build();
        model.addAttribute("answerForm", request);
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/update/{id}")
    public String answerUpdate(@Valid @ModelAttribute("answerForm") AnswerRequest request, BindingResult bindingResult
            , @PathVariable("id") int id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }

        AnswerResponse answerResponse = answerService.findByOne(id);
        if (!answerResponse.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        answerService.answerUpdate(answerResponse, request);
        return String.format("redirect:/question/detail/%s#answer_%s", answerResponse.getQuestion().getId(), answerResponse.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/delete/{id}")
    public String deleteAnswer(@PathVariable(value = "id") Integer id, Principal principal) {
        AnswerResponse answerResponse = answerService.findByOne(id);
        if (!answerResponse.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        answerService.answerDelete(id);
        return String.format("redirect:/question/detail/%s", answerResponse.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        AnswerResponse answerResponse = answerService.findByOne(id);
        SiteUser siteUser = userService.find(principal.getName());
        answerService.answerVote(answerResponse, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", answerResponse.getQuestion().getId(), answerResponse.getId());
    }
}
