package com.hyundai.higher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String memberForm(Model model) {

		log.info("main");

		return "main";
	}

}
