package com.mysite.csJpa.question;

import com.mysite.csJpa.answer.AnswerService;
import com.mysite.csJpa.answer.dto.AddAnswerRequest;
import com.mysite.csJpa.answer.dto.AnswerRequest;
import com.mysite.csJpa.answer.dto.AnswerResponse;
import com.mysite.csJpa.category.CategoryService;
import com.mysite.csJpa.category.dto.CategoryResponse;
import com.mysite.csJpa.comment.CommentService;
import com.mysite.csJpa.comment.dto.CommentResponse;
import com.mysite.csJpa.common.paging.SearchDto;
import com.mysite.csJpa.question.dto.*;
import com.mysite.csJpa.user.SiteUser;
import com.mysite.csJpa.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final CategoryService categoryService;

    @ModelAttribute("categoryList")
    public List<CategoryResponse> getCategoryList() {
        return categoryService.findAll();
    }

    /*
    @GetMapping("/question/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page
                        , @RequestParam(value="kw", defaultValue = "") String kw
                        , @RequestParam(value="categoryId", defaultValue = "1") int categoryId) {

        Page<QuestionResponse> paging = questionService.findAll(page, kw, categoryId);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("categoryId", categoryId);
        return "question_list";
    }
    */

    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable(value = "id") int id, @ModelAttribute(value = "answer") AnswerRequest answerRequest
                        , @RequestParam(value = "page", defaultValue = "0") int page) {
        // Question
        QuestionResponse question = questionService.findByOne(id);
        // Answer
        Page<AnswerResponse> paging = answerService.findAnswerPage(id, page);
        // Comment
        List<CommentResponse> comment = commentService.findByQuestionId(id);

        model.addAttribute("question", question);
        model.addAttribute("paging", paging);
        model.addAttribute("comment", comment);

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/add")
    public String addForm(@ModelAttribute("question") QuestionResponse response) {
        return "question_addForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/add")
    public String addQuestion(@Valid @ModelAttribute("question") QuestionRequest request, BindingResult bindingResult
                                , Principal principal
                                , @RequestParam(value = "categoryId") int categoryId) {
        if (bindingResult.hasErrors()) {
            return "question_addForm";
        }

        questionService.save(request, userService.find(principal.getName()), categoryId);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/update/{id}")
    public String updateQuestion(@ModelAttribute("question") QuestionResponse response, @PathVariable("id") Integer id, Principal principal, Model model) {
        QuestionResponse question = questionService.findByOne(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        model.addAttribute("question", question);
        return "question_addForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/update/{id}")
    public String updateQuestion(@Valid @ModelAttribute("question") QuestionRequest request
            , BindingResult bindingResult, Principal principal
            , @PathVariable("id") Integer id
            , @RequestParam(value = "categoryId") int categoryId) {

        if(bindingResult.hasErrors()) {
            return "question_addForm";
        }
        QuestionResponse question = questionService.findByOne(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionService.update(question, request.getSubject(), request.getContent(), categoryId);
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable(value = "id") Integer id, Principal principal) {
        QuestionResponse question = questionService.findByOne(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        questionService.delete(id);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        QuestionResponse question = questionService.findByOne(id);
        SiteUser siteUser = userService.find(principal.getName());
        questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    /*@GetMapping("/test")
    public ResponseEntity<?> getListBoard(QuestionResponse questionResponse, @PageableDefault(size = 10) Pageable pageable) {
        System.out.println("QuestionController.getListBoard");
        SearchDto params = SearchDto.builder()
                            .categoryId(questionResponse.getCategoryId())
                            .subject(questionResponse.getSubject())
                            .build();
        return ResponseEntity.ok(questionService.findAllTest(params, pageable));
    }*/

    @GetMapping("/question/list")
    public String getList(Model model
                        , QuestionResponse questionResponse
                        , @PageableDefault(size = 10) Pageable pageable) {

        SearchDto params = SearchDto.builder()
                .categoryId(questionResponse.getCategoryId())
                .subject(questionResponse.getSubject())
                .build();

        Page<QuestionResponse> paging = questionService.findAll(params, pageable);

        model.addAttribute("paging", paging);
        // 검색 후 검색창에 기존에 데이터를 고정하기 위해
        model.addAttribute("subject", questionResponse.getSubject());
        model.addAttribute("categoryId", questionResponse.getCategoryId());
        return "question_list";
    }

}
