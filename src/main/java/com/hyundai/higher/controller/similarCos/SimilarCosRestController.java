package com.hyundai.higher.controller.similarCos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.service.similarCos.SimilarCosService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 03. 27.
 * @FileName: SimilarCosRestController.java
 * @author : 신수진
 * @설명 : 매칭 및 유사 상품 검색 기능 컨트롤러
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------  
 * 2023. 03. 30.	신수진		상품 검색 기능 구현   
 * </pre>
 */
@Log4j2
@RestController
@RequestMapping("/similarCosRest")
public class SimilarCosRestController {

	@Autowired
	private SimilarCosService service;
	
	// 검색어 추천 기능 AJAX
	@GetMapping("/checkKeyword")
	public List<ProductDTO> prodList(@RequestParam("keyword") String keyword){
		
		log.info(keyword);
		log.info(service.prodNameList(keyword));
		return service.prodNameList(keyword);
	}
	
	// 유사성분템 검색 AJAX
	@GetMapping("/searchProd")
	public List<ProductDTO> searchProd(@RequestParam("searchKeyword") String searchKeyword) {
		
		return service.prodList(searchKeyword);
	}

}
