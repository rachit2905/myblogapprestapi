package com.springboot.myblogapp.myblogapprestapi.payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.springboot.myblogapp.myblogapprestapi.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "post title should have atleast 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "post title should have atleast 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

}
