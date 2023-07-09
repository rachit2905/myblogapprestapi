package com.springboot.myblogapp.myblogapprestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.myblogapp.myblogapprestapi.model.post;

public interface postRepository extends JpaRepository<post, Long> {

}
