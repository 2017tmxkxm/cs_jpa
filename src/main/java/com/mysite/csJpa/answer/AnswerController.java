package com.mysite.csJpa.answer;

import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.question.QuestionService;
import com.mysite.csJpa.user.SiteUser;
import com.mysite.csJpa.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        answerService.save(new AddAnswerRequest(answer.getContent(), question, LocalDateTime.now(), userService.find(principal.getName())));
        return String.format("redirect:/question/detail/%s", questionId);
    }
}
