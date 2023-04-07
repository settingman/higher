package com.hyundai.higher.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.category.CategoryAllDTO;
import com.hyundai.higher.service.category.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping("/cateAll")
	public CategoryAllDTO cateAll() {
		
		return service.cateAll();
	}
	
	
}
