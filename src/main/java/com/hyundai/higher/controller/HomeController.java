package com.hyundai.higher.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hyundai.higher.service.include.IncludeService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class HomeController {

	@Autowired(required=true)
	private IncludeService iService;
	
	@GetMapping(value = "/")
	public String memberForm(Model model) {

		log.info("main");

		model.addAttribute("categoryList", iService.categoryListAll());
		
		return "main";
	}

}
