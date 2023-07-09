package com.springboot.myblogapp.myblogapprestapi.Services;

import com.springboot.myblogapp.myblogapprestapi.exceptions.BlogApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    private com.springboot.myblogapp.myblogapprestapi.repository.UserRepository userRepository;
    private com.springboot.myblogapp.myblogapprestapi.repository.RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private com.springboot.myblogapp.myblogapprestapi.Security.JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
            com.springboot.myblogapp.myblogapprestapi.repository.UserRepository userRepository,
            com.springboot.myblogapp.myblogapprestapi.repository.RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            com.springboot.myblogapp.myblogapprestapi.Security.JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(com.springboot.myblogapp.myblogapprestapi.payload.LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(com.springboot.myblogapp.myblogapprestapi.payload.SignUpDto registerDto) {

        // add check for username exists in database
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        com.springboot.myblogapp.myblogapprestapi.model.User user = new com.springboot.myblogapp.myblogapprestapi.model.User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<com.springboot.myblogapp.myblogapprestapi.model.Role> roles = new HashSet<>();
        com.springboot.myblogapp.myblogapprestapi.model.Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }
}