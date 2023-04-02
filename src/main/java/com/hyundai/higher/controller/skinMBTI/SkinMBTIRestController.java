package com.hyundai.higher.controller.skinMBTI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
import com.hyundai.higher.service.skinMBTI.SkinMBTIService;
import com.sun.tools.sjavac.Log;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/skinMBTIRest")
public class SkinMBTIRestController {

	@Autowired
	private SkinMBTIService service;
	
	@GetMapping("/saveMBTI")
	public void updateMBTI(@RequestParam("mbti") String mbti) {
		service.updateMBTI("angz", mbti);
	}
}
