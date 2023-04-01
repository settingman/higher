package com.hyundai.higher.controller.skinMBTI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
import com.hyundai.higher.service.skinMBTI.SkinMBTIService;

@RestController
@RequestMapping("/skinMBTIRest")
public class SkinMBTIRestController {

	@Autowired
	private SkinMBTIService service;
	
	// MBTI 설명
	@GetMapping("/getData")
	public SkinMBTIDTO getSkinMBTIData(@RequestParam("stype") String stype) {
				
		return service.selectSkinMBTI(stype);
	}
	
	@PostMapping("/update")
	public void updateMBTI(@RequestParam("mbti") String mbti) {
		
	}
}
