package com.hyundai.higher.controller.compare;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 03. 23.
 * @FileName: CompareController.java
 * @author : 박서현
 * @설명 : 상품 비교 컨트롤러
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 23.     박서현       최초 생성
 * </pre>
 */

@Log4j2
@RequestMapping("/product")
@Controller
public class CompareController {

	
	// 상품 비교 페이지 이동
	@GetMapping("/compare")
	public String compare(Model model) {
		
		return "product/compare";
	}

}
