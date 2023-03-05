package com.hyundai.higher.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;



/**
 * @since   : 2023. 3. 3.
 * @FileName: HomeController.java
 * @author  : 박성환
 * @설명    : 메인 페이지.

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 27.     박성환      	최초 생성
 * </pre>
 */
@Slf4j
@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String memberForm(Model model) {

		log.info("main");

		return "main";
	}
	
	@GetMapping(value = "/test")
	public String test(Model model) {

		log.info("main");

		return "test";
	}

}
