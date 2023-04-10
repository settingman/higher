package com.hyundai.higher.controller.skinMBTI;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.makeup.MbtiVO;
import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
import com.hyundai.higher.service.makeup.MakeupService;
import com.hyundai.higher.service.skinMBTI.SkinMBTIService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 03. 16.
 * @FileName: SkinMBTIController.java
 * @author : 신수진
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 16.    신수진    	최초 생성   
 * 2023. 04. 10.	신수진		결과 페이지 수정
 * </pre>
 */

@Log4j2
@RequestMapping("/skinMBTI")
@Controller
public class SkinMBTIController {

	@Autowired
	private SkinMBTIService service;
	
	@Autowired
	private MakeupService makeup;
	
	@Autowired
	private SkinMBTIService mbtiser;
	
	// 메인
	@GetMapping("/selfConsult")
	public String skinMBTIMain(Model model) {
		
		return "skinMBTI/selfConsult";
	}
	
	// 결과
	@PostMapping("/result")
	public String skinMBTIResult(@RequestParam("mbti") String mbti, 
			@RequestParam("score1") int score1, @RequestParam("score2") int score2, 
			@RequestParam("score3") int score3, @RequestParam("score4") int score4, Model model) {

		int[] scores = {score1, score2, score3, score4};
		
		model.addAttribute("mbti", service.selectSkinMBTI(mbti));
		model.addAttribute("scores", scores);
		
		return "skinMBTI/result";
	}
	
	// 회원 skinmbti 결과 확인
	@GetMapping("/check_result")
	public String resultCheck(Principal principal, Model model) {
		
		MbtiVO info = new MbtiVO();
		info = makeup.findmbti(principal.getName());
		
		String[] score = info.getMbti_scores().split(",");
		
		int[] scores = new int[4];
		for(int i=0; i<4; i++) {
			scores[i] = Integer.parseInt(score[i]);
		}
		
		SkinMBTIDTO mbti = new SkinMBTIDTO();
		mbti = mbtiser.selectSkinMBTI(info.getMbti());
		
		model.addAttribute("mbti", mbti);
		model.addAttribute("scores", scores);
		
		return "skinMBTI/result";
	}
	
	
	
}
