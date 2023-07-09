package com.springboot.myblogapp.myblogapprestapi.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.myblogapp.myblogapprestapi.exceptions.ResourceNotFoundException;
import com.springboot.myblogapp.myblogapprestapi.model.post;
import com.springboot.myblogapp.myblogapprestapi.payload.PostDto;
import com.springboot.myblogapp.myblogapprestapi.payload.PostResponse;
import com.springboot.myblogapp.myblogapprestapi.repository.postRepository;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class PostServiceImpl implements PostService {
    private postRepository postsRepository;
    private ModelMapper mapper;

    public PostServiceImpl(postRepository postsRepositorys, ModelMapper mapper) {
        {
            this.postsRepository = postsRepositorys;
            this.mapper = mapper;
        }

    }

    @Override
    public PostDto createPost(PostDto postDto) {
        post posts = MaptoEntity(postDto);
        post newPost = postsRepository.save(posts);

        PostDto postResponses = mapToDto(newPost);

        return postResponses;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<post> postss = postsRepository.findAll(pageable);
        List<post> postsList = postss.getContent();
        List<PostDto> content = postsList.stream().map(posts -> mapToDto(posts)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postss.getNumber());
        postResponse.setPageSize(postss.getSize());
        postResponse.setTotalElements(postss.getTotalElements());
        postResponse.setLast(postss.isLast());
        return postResponse;
    }

    private PostDto mapToDto(post posts) {
        PostDto postDto = mapper.map(posts, PostDto.class);

        // postDto.setComments(posts.getComments());
        return postDto;

    }

    private post MaptoEntity(PostDto postDto) {
        post posts = mapper.map(postDto, post.class);
        // posts.setComments(postDto.getComments());
        return posts;
    }

    public PostDto getPostById(long id) {
        post temp = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(temp);
    }

    public PostDto updatePost(PostDto postDto, long id) {
        post temp = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        temp.setContent(postDto.getContent());
        temp.setDescription(postDto.getDescription());
        temp.setTitle(postDto.getTitle());
        // temp.setComments(postDto.getComments());
        post updatedPost = postsRepository.save(temp);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {

        post temp = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postsRepository.delete(temp);
    }
}
