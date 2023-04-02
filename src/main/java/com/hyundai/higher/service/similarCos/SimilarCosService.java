package com.hyundai.higher.service.similarCos;

import java.util.List;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;

public interface SimilarCosService {
	
	// 성분유사템 세부 페이지 - 추천
	public List<ProductDetailDTO> recogProducts(String pcode);
	
	// 상품명 검색 조회 AJAX
	public List<ProductDTO> prodNameList(String keyword);
	
	// 상품 검색 AJAX
	public List<ProductDTO> prodList(String keyword);

	// 인기 상품 목록
	public List<ProductDTO> bestProdList();
}
