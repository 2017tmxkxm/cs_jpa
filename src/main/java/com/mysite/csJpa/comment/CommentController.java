package com.mysite.csJpa.comment;

import com.mysite.csJpa.answer.AnswerService;
import com.mysite.csJpa.answer.dto.AnswerResponse;
import com.mysite.csJpa.comment.dto.CommentRequest;
import com.mysite.csJpa.comment.dto.CommentResponse;
import com.mysite.csJpa.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/save")
    public String saveComment(@ModelAttribute("comment") CommentRequest request, Principal principal) {
        CommentRequest saveCommentRequest = CommentRequest.builder()
                .content(request.getContent())
                .author(userService.find(principal.getName()))
                .answer(request.getAnswer())
                .question(request.getQuestion())
                .createDate(LocalDateTime.now())
                .build();
        CommentResponse savedDto = commentService.save(saveCommentRequest);
        return String.format("redirect:/question/detail/%s#answer_%s", savedDto.getQuestion().getId(), savedDto.getAnswer().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/update/{commentId}")
    public String updateCommentForm(@PathVariable("commentId") int commentId, Principal principal, Model model) {

        CommentResponse comment = commentService.findById(commentId);
        if(!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        comment = CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .question(comment.getQuestion())
                .build();

        model.addAttribute("commentForm", comment);
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/update/{commentId}")
    public String updateComment(@PathVariable(value = "commentId") int commentId
                                , @Valid @ModelAttribute("commentForm") CommentRequest commentRequest
                                , BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "comment_form";
        }

        // content를 제외한 필드의 값을 가져와서 넘겨주기 위해
        CommentResponse commentResponse = commentService.findById(commentId);
        commentService.updateComment(commentRequest, commentResponse);

        return String.format("redirect:/question/detail/%s#comment_%s", commentResponse.getQuestion().getId(), commentResponse.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable(value = "commentId") int commentId) {
        CommentResponse commentResponse = commentService.findById(commentId);
        commentService.deleteComment(commentId);
        return String.format("redirect:/question/detail/%s#answer%s", commentResponse.getQuestion().getId(), commentResponse.getAnswer().getId());
    }

}
