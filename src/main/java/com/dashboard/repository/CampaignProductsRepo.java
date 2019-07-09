package com.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.dashboard.model.CampaignProducts;


@Component
public interface CampaignProductsRepo extends JpaRepository<CampaignProducts, Integer> {

	}

