package com.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.dashboard.model.Campaign;


	@Component
	public interface CampaignRepo extends JpaRepository<Campaign, Integer> {

	}

