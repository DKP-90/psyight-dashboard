package com.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.dashboard.model.Products;;

@Component
public interface ProductsRepo extends JpaRepository<Products, Integer> {

}