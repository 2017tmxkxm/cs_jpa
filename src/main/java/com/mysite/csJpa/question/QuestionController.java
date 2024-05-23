package com.mysite.csJpa.question;

import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.dto.AddQuestionRequest;
import com.mysite.csJpa.question.dto.QuestionListViewResponse;
import com.mysite.csJpa.question.dto.QuestionViewResponse;
import com.mysite.csJpa.question.dto.UpdateQuestionRequest;
import com.mysite.csJpa.user.SiteUser;
import com.mysite.csJpa.user.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/question/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<QuestionListViewResponse> paging = questionService.findAll(page);

        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable(value = "id") int id, @ModelAttribute(value = "answer") AddAnswerRequest answer) {
        Question question = questionService.findByOne(id);
        model.addAttribute("question", new QuestionViewResponse(question));
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/add")
    public String addForm(Model model) {
        model.addAttribute("question", new AddQuestionRequest());
        return "question_addForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/add")
    public String addQuestion(@Valid @ModelAttribute("question") AddQuestionRequest question, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_addForm";
        }
        questionService.save(question, userService.find(principal.getName()));
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/update/{id}")
    public String updateQuestion(@ModelAttribute("question") UpdateQuestionRequest request, @PathVariable("id") Integer id, Principal principal, Model model) {
        Question question = questionService.findByOne(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        request = UpdateQuestionRequest.builder()
                .subject(question.getSubject())
                .content(question.getContent())
                .build();
        model.addAttribute("question", request);
        return "question_addForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/update/{id}")
    public String updateQuestion(@Valid @ModelAttribute("question") UpdateQuestionRequest request
            , BindingResult bindingResult, Principal principal
            , @PathVariable("id") Integer id) {
        if(bindingResult.hasErrors()) {
            return "question_addForm";
        }
        Question question = questionService.findByOne(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionService.update(question, request.getSubject(), request.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable(value = "id") Integer id, Principal principal) {
        Question question = questionService.findByOne(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        questionService.delete(id);
        return "redirect:/";
    }
}
