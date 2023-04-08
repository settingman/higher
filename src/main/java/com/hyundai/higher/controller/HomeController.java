package com.hyundai.higher.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hyundai.higher.mapper.beauty.BeautyMapper;
import com.hyundai.higher.security.dto.SecurityMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 3. 3.
 * @FileName: HomeController.java
 * @author : 박성환
 * @설명 : 메인 페이지.
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 27.     박성환      	최초 생성
 *     </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final BeautyMapper beautyMapper;

	@GetMapping(value = "/")
	public String memberForm(Model model) {

		log.info("main");

		return "main";
	}

	@GetMapping(value = "/test")
	public String test(Model model, @AuthenticationPrincipal SecurityMember securityMember ) {

		log.info(securityMember.toString());
		
		securityMember.setMbti("test");

		return "test";
	}

	@GetMapping(value = "/test3")
	public String test2(Model model) {

	
		return "test3";
	}

	@GetMapping(value = "/test4")
	public String test4(Model model) {

		return "test4";
	}

	@GetMapping(value = "/test5")
	public String test5(Model model) {

	
		return "test5";
	}
}
