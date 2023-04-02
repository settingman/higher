package com.hyundai.higher.controller.skinMBTI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.service.include.IncludeService;
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
 * 2023. 03. 16.     신수진       
 * </pre>
 */

@Log4j2
@RequestMapping("/skinMBTI")
@Controller
public class SkinMBTIController {

	@Autowired(required=true)
	private IncludeService iService;
	
	@Autowired
	private SkinMBTIService service;
	
	// 메인
	@GetMapping("/selfConsult")
	public String skinMBTIMain(Model model) {
		
		return "/skinMBTI/selfConsult";
	}
	
	// 결과
	@GetMapping("/result")
	public String skinMBTIResult(@RequestParam("mbti") String mbti, 
			@RequestParam("score1") int score1, @RequestParam("score2") int score2, 
			@RequestParam("score3") int score3, @RequestParam("score4") int score4, Model model) {

		int[] scores = {score1, score2, score3, score4};
		
		model.addAttribute("mbti", service.selectSkinMBTI(mbti));
		model.addAttribute("scores", scores);
		
		return "/skinMBTI/result";
	}
	
	
	
}
