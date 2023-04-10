package com.hyundai.higher.controller.skinMBTI;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.security.dto.SecurityMember;
import com.hyundai.higher.service.skinMBTI.SkinMBTIService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/skinMBTIRest")
public class SkinMBTIRestController {

	@Autowired
	private SkinMBTIService service;
	
	@PostMapping("/saveMBTI")
	public void updateMBTI(@RequestParam("mbti") String mbti, @RequestParam("mbti_scores") String mbti_scores, Principal prin, @AuthenticationPrincipal SecurityMember securityMember) {
		String mid = prin.getName();
		
		log.info(mid);
		log.info(mbti_scores);
		
		securityMember.setMbti(mbti);
		
		service.updateMBTI(mid, mbti, mbti_scores);
	}
}
