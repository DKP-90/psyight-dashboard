package com.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.dashboard.model.User;

@Component
public interface UserRepo extends JpaRepository<User, Integer> {

}