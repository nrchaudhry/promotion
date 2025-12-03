package com.cwiztech.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwiztech.promotion.model.PromotionModel;
import com.cwiztech.promotion.repository.promotionRepository;


@RestController
@CrossOrigin
@RequestMapping("/promotion")
public class promotion {
	
	@Autowired
	private promotionRepository repo;
	
	
	@GetMapping("/getAll")
	public List<PromotionModel> get() {

		List<PromotionModel> promotion = repo.findAll();

		return promotion;
	}

}
