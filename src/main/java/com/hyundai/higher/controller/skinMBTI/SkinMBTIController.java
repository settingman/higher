package com.hyundai.higher.controller.skinMBTI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyundai.higher.service.include.IncludeService;

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

@RequestMapping("/skinMBTI")
@Controller
public class SkinMBTIController {

	@Autowired(required=true)
	private IncludeService iService;

	@GetMapping("/main")
	public String skinMBTIMain(Model model) {
		
		model.addAttribute("categoryList", iService.categoryListAll());
		
		return "/skinMBTI/main";
	}
	
}
