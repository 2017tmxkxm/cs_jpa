package com.mysite.csJpa.comment;

import com.mysite.csJpa.DataNotFoundException;
import com.mysite.csJpa.comment.dto.CommentRequest;
import com.mysite.csJpa.comment.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * 답변에 대한 댓글 저장
     * @param commentRequest
     * @return CommentResponse
     */
    public CommentResponse save(CommentRequest commentRequest) {
        Comment savedEntity = commentRepository.save(commentRequest.toEntity());
        return CommentResponse.builder()
                .id(savedEntity.getId())
                .answer(savedEntity.getAnswer())
                .question(savedEntity.getQuestion())
                .build();
    }

    /**
     * 댓글 리스트 조회
     * @param questionId
     * @return List<CommentResponse>
     */
    public List<CommentResponse> findByQuestionId(int questionId) {
        List<Comment> commentList = commentRepository.findByQuestionId(questionId);
        return commentList.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 댓글 조회
     * @param commentId
     * @return CommentResponse
     */
    public CommentResponse findById(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("comment not found : " + commentId));
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .question(comment.getQuestion())
                .answer(comment.getAnswer())
                .author(comment.getAuthor())
                .createDate(comment.getCreateDate())
                .modifyDate(comment.getModifyDate())
                .build();
    }

    /**
     * 댓글 수정
     * @param request
     * @param response
     */
    public void updateComment(CommentRequest request, CommentResponse response) {
        request = CommentRequest.builder()
                            .id(response.getId())
                            .content(request.getContent())
                            .author(response.getAuthor())
                            .question(response.getQuestion())
                            .answer(response.getAnswer())
                            .createDate(response.getCreateDate())
                            .modifyDate(LocalDateTime.now())
                            .build();
        commentRepository.save(request.toEntity());
    }

    /**
     * 댓글 삭제
     * @param commentId
     */
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("comment not found : " + commentId));
        commentRepository.delete(comment);
    }

}
