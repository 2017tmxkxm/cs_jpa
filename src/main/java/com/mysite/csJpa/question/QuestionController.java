package com.mysite.csJpa.question;

import com.mysite.csJpa.question.dto.QuestionListViewResponse;
import com.mysite.csJpa.question.dto.QuestionViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<QuestionListViewResponse> questionList = questionService.getList().stream()
                .map(QuestionListViewResponse::new)
                .toList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable(value = "id") int id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", new QuestionViewResponse(question));
        return "question_detail";
    }
}
