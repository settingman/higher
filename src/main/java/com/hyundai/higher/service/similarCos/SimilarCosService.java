package com.hyundai.higher.service.similarCos;

import java.util.List;

import com.hyundai.higher.domain.similarCos.SimilarCosDTO;

public interface SimilarCosService {
	
	// 성분유사템 세부 페이지 - 추천
	public List<SimilarCosDTO> recogProducts(String pcode);

}
