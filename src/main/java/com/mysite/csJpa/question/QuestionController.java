package com.mysite.csJpa.question;

import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.dto.AddQuestionRequest;
import com.mysite.csJpa.question.dto.QuestionListViewResponse;
import com.mysite.csJpa.question.dto.QuestionViewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<QuestionListViewResponse> questionList = questionService.findAll().stream()
                .map(QuestionListViewResponse::new)
                .toList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable(value = "id") int id, @ModelAttribute(value = "answer") AddAnswerRequest answer) {
        Question question = questionService.findByOne(id);
        model.addAttribute("question", new QuestionViewResponse(question));
        return "question_detail";
    }

    @GetMapping("/question/add/form")
    public String addForm(Model model) {
        model.addAttribute("question", new AddQuestionRequest());
        return "question_addForm";
    }

    @PostMapping("/question/add")
    public String addQuestion(@Valid @ModelAttribute("question") AddQuestionRequest question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_addForm";
        }
        questionService.save(question);
        return "redirect:/question/list";
    }
}
