package com.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.dashboard.model.Groups;

@Component
public interface GroupsRepo extends JpaRepository<Groups, Integer> {

}