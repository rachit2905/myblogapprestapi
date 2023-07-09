package com.springboot.myblogapp.myblogapprestapi.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty(message = "Name shoud be not empty")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Comment must be min 10 characters")
    private String body;

}
