package com.springboot.myblogapp.myblogapprestapi.Services;

import com.springboot.myblogapp.myblogapprestapi.payload.SignUpDto;

public interface AuthService {
    String login(com.springboot.myblogapp.myblogapprestapi.payload.LoginDto loginDto);

    String register(SignUpDto registerDto);
}