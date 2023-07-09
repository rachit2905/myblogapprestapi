package com.springboot.myblogapp.myblogapprestapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.myblogapp.myblogapprestapi.Services.CommentService;
import com.springboot.myblogapp.myblogapprestapi.payload.CommentDto;

@RestController
@RequestMapping("/api/")

public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postsId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long id,
            @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postsId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postsId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable(value = "postsId") Long postsId,
            @PathVariable(value = "commentId") Long commentID) {
        CommentDto commentDto = commentService.getCommentsById(postsId, commentID);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);

    }

    @PutMapping("/posts/{postsId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentsById(@PathVariable(value = "postsId") Long postsId,
            @PathVariable(value = "commentId") Long commentID, @Valid @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateCommentsById(postsId, commentID, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postsId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentsById(@PathVariable(value = "postsId") Long postsId,
            @PathVariable(value = "commentId") Long commentID) {
        commentService.deleteCommentsById(postsId, commentID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
