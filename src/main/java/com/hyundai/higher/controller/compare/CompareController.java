package com.hyundai.higher.controller.compare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyundai.higher.service.include.IncludeService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 03. 23.
 * @FileName: CompareController.java
 * @author : 박서현
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 23.     박서현       최초 생성
 * </pre>
 */

@Log4j2
@RequestMapping("/compare")
@Controller
public class CompareController {

	@Autowired(required=true)
	private IncludeService iService;
	
	// 상품 비교 페이지
	@GetMapping("/main")
	public String compare(Model model) {

		model.addAttribute("categoryList", iService.categoryListAll());
		
		return "compare/main";
	}

}
