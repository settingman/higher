package com.hyundai.higher.service.product;

import java.util.List;

import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;

/**
 * @since : 2023. 3. 16.
 * @FileName: ProductService.java
 * @author : 신수진
 * @설명 : 
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 16.     신수진      		
 *     </pre>
 */
public interface ProductService {
	
	// 상품 목록
	public List<ProductDTO> productList(String dept1no, String dept2no);
	
	// 상품 세부 정보 전체
	public ProductDetailDTO productDetail(String pcode);
}
