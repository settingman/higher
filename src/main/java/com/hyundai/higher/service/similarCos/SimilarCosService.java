package com.hyundai.higher.service.similarCos;

import java.util.List;

import com.hyundai.higher.domain.product.ProductDetailDTO;

public interface SimilarCosService {
	
	public  List<ProductDetailDTO> recogProducts(String pcode);

}
