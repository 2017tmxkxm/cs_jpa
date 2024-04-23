package com.mysite.csJpa.answer;

import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/answer/create/{id}")
    public String createAnswer(@PathVariable("id") int questionId, @RequestParam(value = "content") String content) {
        Question question = questionService.getQuestion(questionId);
        answerService.save(new AddAnswerRequest(content, question, LocalDateTime.now()));
        return String.format("redirect:/question/detail/%s", questionId);
    }
}
