package com.springboot.myblogapp.myblogapprestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.myblogapp.myblogapprestapi.model.comment;
import java.util.List;

public interface commentRepository extends JpaRepository<comment, Long> {
    List<comment> findByPostsId(Long postId);
}
