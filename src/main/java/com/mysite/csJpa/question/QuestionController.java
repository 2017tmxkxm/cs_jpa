package com.mysite.csJpa.question;

import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.dto.AddQuestionRequest;
import com.mysite.csJpa.question.dto.QuestionListViewResponse;
import com.mysite.csJpa.question.dto.QuestionViewResponse;
import com.mysite.csJpa.user.SiteUser;
import com.mysite.csJpa.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @GetMapping("/question/add/form")
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
}
