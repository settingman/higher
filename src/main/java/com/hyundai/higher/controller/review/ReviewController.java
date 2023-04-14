package com.hyundai.higher.controller.review;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyundai.higher.service.review.ReviewService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 04. 14.
 * @FileName: ReviewController.java
 * @author : 신수진
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 14.    신수진		최초 생성
 * </pre>
 */

@Log4j2
@RequestMapping("/review")
@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService service;
	
	@GetMapping("/list")
	public String reviewList() {
		return "review/list";
	}
	
	@GetMapping("/form")
	public String reviewForm(Model model, Principal prin) {
		
		String mid = prin.getName();
		
		// 회원이 작성 가능한 리뷰 상품 목록 
		model.addAttribute("reviewProdList", service.reviewProdList(mid));
		
		return "review/form";
	}

}
