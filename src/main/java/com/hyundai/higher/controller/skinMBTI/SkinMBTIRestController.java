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
/**
 * @since : 2023. 04. 10.
 * @FileName: SkinMBTIRestController.java
 * @author : 신수진
 * @설명 : 피부 진단 결과 저장 및 포인트 지급
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 10.    신수진    	최초 생성   
 * 2023. 04. 10.	신수진		피부 진단 결과 저장 기능 구현
 * 2023. 04. 12.	신수진		최초 진단 시 포인트 적립 기능 추가
 * </pre>
 */
@Log4j2
@RestController
@RequestMapping("/skinMBTIRest")
public class SkinMBTIRestController {

	@Autowired
	private SkinMBTIService service;
	
	// 피부 mbti 결과 저장 + 포인트 지급
	@PostMapping("/saveMBTI")
	public void updateMBTI(@RequestParam("mbti") String mbti, @RequestParam("mbti_scores") String mbti_scores, Principal prin, 
				@AuthenticationPrincipal SecurityMember securityMember) {
		String mid = prin.getName();
		
		if(securityMember.getMbti() == null) {
			service.addPoint(mid);
		}
		
		securityMember.setMbti(mbti);
		
		service.updateMBTI(mid, mbti, mbti_scores);
		
		
	}
}
