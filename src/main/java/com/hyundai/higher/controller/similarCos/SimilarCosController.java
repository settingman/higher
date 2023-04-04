package com.hyundai.higher.controller.similarCos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.service.product.ProductService;
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
 * 2023. 03. 27.    신수진    
 * 2023. 03. 30.	신수진		상품 검색 기능 구현   
 * 2023. 04. 01.	신수진		유사성분템 세부 페이지 기능 구현
 * </pre>
 */
@Log4j2
@RequestMapping("/similarCos")
@Controller
public class SimilarCosController {
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private SimilarCosService service;
	
	// 유사성분템 메인
	@GetMapping("/main")
	public String similarCosMain(Model model) {
		
		model.addAttribute("bestProdList", service.bestProdList());
		
		return "similarCos/findSimilar";
	}
	
	// 유사성분템 세부
	@GetMapping("/detail")
	public String similarCosDetail(@RequestParam("pcode") String pcode, Model model) {
		log.info("pdoce :  " + pcode);
		
		ProductDetailDTO product = pService.productDetail(pcode);
		String ingredient = product.getProductDTO().getPingredient();
		ingredient = ingredient.replaceAll(",", " ").replaceAll("\\s+", " ");;
		String[] iList = ingredient.split(" ");
		String[] mainIngredient = new String[6];
		
		int idx = 0;
		
		for(String i : iList) {
			i = i.trim();
			if(i.equals("정제수")) continue;
			if(i.equals("글리세린")) continue;
			
			mainIngredient[idx] = i;
			idx++;
			if(idx == 6) break;
		}
		
		model.addAttribute("product", product);
		model.addAttribute("mainIngredient", mainIngredient);
		service.recogProducts(pcode);
		
		return "similarCos/detail";
	}
	

}
