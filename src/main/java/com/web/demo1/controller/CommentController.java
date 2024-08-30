package com.web.demo1.controller;

import com.web.demo1.dto.comment.response.CommentResponseDto;
import com.web.demo1.dto.comment.request.CommentSaveRequestDto;
import com.web.demo1.dto.comment.response.CommentSaveResponseDto;
import com.web.demo1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/board/{boardId}/comments")
    public CommentSaveResponseDto saveComment (@PathVariable Long boardId,
                                               @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.saveComment(boardId, commentSaveRequestDto);
    }

    @GetMapping("/boards/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }



}
