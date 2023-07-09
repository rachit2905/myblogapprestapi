package com.springboot.myblogapp.myblogapprestapi.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.myblogapp.myblogapprestapi.exceptions.BlogApiException;
import com.springboot.myblogapp.myblogapprestapi.exceptions.ResourceNotFoundException;
import com.springboot.myblogapp.myblogapprestapi.model.comment;
import com.springboot.myblogapp.myblogapprestapi.model.post;
import com.springboot.myblogapp.myblogapprestapi.payload.CommentDto;
import com.springboot.myblogapp.myblogapprestapi.repository.commentRepository;
import com.springboot.myblogapp.myblogapprestapi.repository.postRepository;

@Service
public class CommentServiceImpl implements CommentService {
    private commentRepository commentsRepository;
    private postRepository postsRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(commentRepository commentsRepository, postRepository postsRepository,
            ModelMapper mapper) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
        this.mapper = mapper;
    }

    public CommentDto createComment(Long postId, CommentDto commentDto) {
        comment temp = maptoEntity(commentDto);
        post tempPost = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        temp.setPosts(tempPost);
        comment newComment = commentsRepository.save(temp);
        return mapToDto(newComment);
    }

    private CommentDto mapToDto(comment comments) {
        CommentDto commentDto = mapper.map(comments, CommentDto.class);

        return commentDto;

    }

    private comment maptoEntity(CommentDto commentDto) {
        comment comments = mapper.map(commentDto, comment.class);
        return comments;
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<comment> Comments = commentsRepository.findByPostsId(postId);
        return Comments.stream().map(comments -> mapToDto(comments)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long CommentId) {
        post Post = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        comment Comment = commentsRepository.findById(CommentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", CommentId));
        if (!Comment.getPosts().getId().equals(Post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to a post");
        }
        return mapToDto(Comment);
    }

    @Override
    public CommentDto updateCommentsById(Long postId, Long CommentId, CommentDto commentRequest) {
        post Post = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        comment Comment = commentsRepository.findById(CommentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", CommentId));
        if (!Comment.getPosts().getId().equals(Post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to a post");
        }
        Comment.setBody(commentRequest.getBody());
        Comment.setName(commentRequest.getName());
        Comment.setEmail(commentRequest.getEmail());

        comment updatedComment = commentsRepository.save(Comment);
        return mapToDto(updatedComment);
    }

    @Override
    public CommentDto deleteCommentsById(Long postId, Long CommentId) {
        post Post = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        comment Comment = commentsRepository.findById(CommentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", CommentId));
        if (!Comment.getPosts().getId().equals(Post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to a post");
        }
        commentsRepository.delete(Comment);
        return mapToDto(Comment);

    }

}
