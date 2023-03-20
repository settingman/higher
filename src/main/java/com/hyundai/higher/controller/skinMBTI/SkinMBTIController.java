package com.hyundai.higher.controller.skinMBTI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/main")
	public String skinMBTIMain() {
		return "/skinMBTI/main";
	}
	
}
