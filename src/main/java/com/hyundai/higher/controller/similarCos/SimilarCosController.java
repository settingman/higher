package com.hyundai.higher.controller.similarCos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.service.include.IncludeService;
import com.hyundai.higher.service.similarCos.SimilarCosService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 03. 27.
 * @FileName: SimilarCosController.java
 * @author : 신수진
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 27.     신수진       
 * </pre>
 */

@Log4j2
@RequestMapping("/similarCos")
@Controller
public class SimilarCosController {
	
	@Autowired(required=true)
	private IncludeService iService;
	
	@Autowired
	private SimilarCosService service;
	
	// 유사성분템 메인
	@GetMapping("/main")
	public String similarCosMain(Model model) {
		
		model.addAttribute("categoryList", iService.categoryListAll());
		
		return "similarCos/main";
	}
	
	// 유사성분템 세부
	@GetMapping("/detail")
	public String similarCosDetail(@RequestParam("pcode") String pcode, Model model) {
		log.info("pdoce :  " + pcode);
		
		service.recogProducts(pcode);
		
		return "similarCos/detail";
	}
	
	// 유사성분템 검색 결과
	@GetMapping("/search")
	public String similarCosSearch() {
		
		
		return "similarCos/searchResult";
	}

}
