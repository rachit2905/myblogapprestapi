package com.springboot.myblogapp.myblogapprestapi.Services;

import java.util.List;

import com.springboot.myblogapp.myblogapprestapi.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentsById(Long postId, Long CommentId);

    CommentDto updateCommentsById(Long postId, Long CommentId, CommentDto commentRequest);

    CommentDto deleteCommentsById(Long postId, Long CommentId);

}
