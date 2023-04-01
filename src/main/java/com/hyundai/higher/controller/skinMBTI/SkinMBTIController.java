package com.hyundai.higher.controller.skinMBTI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
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
	@GetMapping("/main")
	public String skinMBTIMain(Model model) {
		
		model.addAttribute("categoryList", iService.categoryListAll());
		
		return "/skinMBTI/main2";
	}
	
}
