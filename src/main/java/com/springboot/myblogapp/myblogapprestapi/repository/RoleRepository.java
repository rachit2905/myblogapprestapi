package com.springboot.myblogapp.myblogapprestapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.myblogapp.myblogapprestapi.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findByName(String name);
}
