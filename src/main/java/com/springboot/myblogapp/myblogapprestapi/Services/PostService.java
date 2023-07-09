package com.springboot.myblogapp.myblogapprestapi.Services;

import java.util.List;

import com.springboot.myblogapp.myblogapprestapi.payload.PostDto;
import com.springboot.myblogapp.myblogapprestapi.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
