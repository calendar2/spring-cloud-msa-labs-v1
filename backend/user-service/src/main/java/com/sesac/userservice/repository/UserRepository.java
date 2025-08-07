package com.sesac.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sesac.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
