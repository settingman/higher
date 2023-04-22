package com.hyundai.higher.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.category.CategoryAllDTO;
import com.hyundai.higher.service.category.CategoryService;
/**
 * @since : 2023. 03. 16.
 * @FileName: CategoryController.java
 * @author : 신수진
 * @설명 : 헤더 카테고리 목록 컨틀롤러
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 16.	신수진		전체 카테고리
 * </pre>
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	// 전체 카테고리
	@GetMapping("/cateAll")
	public CategoryAllDTO cateAll() {
		
		return service.cateAll();
	}
	
	
}
