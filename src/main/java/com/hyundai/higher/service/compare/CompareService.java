package com.hyundai.higher.service.compare;

import com.hyundai.higher.domain.product.ProductDTO;

public interface CompareService {

	//비교 상품의 성분 정보 불러오기 
		public ProductDTO getInfo(String pcode);
}
