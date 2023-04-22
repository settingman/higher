package com.hyundai.higher.service.similarCos;

import java.util.List;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.similarCos.SimilarCosDTO;
/**
 * @since : 2023. 04. 01.
 * @FileName: SimilarCosService.java
 * @author : 신수진
 * @설명 : 유사성분제품 추천 서비스, 상품 검색 서비스
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------  
 * 2023. 04. 01.	신수진		상품 검색 기능
 * 2023. 04. 05.	신수진		유사성분제품 조회
 * </pre>
 */
public interface SimilarCosService {
	
	// 성분유사템 세부 페이지 - 추천
	public List<SimilarCosDTO> recogProducts(String pcode);
	
	// 상품명 검색 조회 AJAX
	public List<ProductDTO> prodNameList(String keyword);
	
	// 상품 검색 AJAX
	public List<ProductDTO> prodList(String keyword);
}
