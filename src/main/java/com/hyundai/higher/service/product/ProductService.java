package com.hyundai.higher.service.product;

import java.util.List;

import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.category.MBTIDTO;
import com.hyundai.higher.domain.match.MatchProductDTO;
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
 * 2023. 03. 16.    신수진    	최초 생성
 * 2023. 04. 12.	신수진		피부타입 카테고리 추가  		
 *     </pre>
 */
public interface ProductService {
	
	// 상품 목록
	public List<ProductDTO> productList(String dept1no, String dept2no, String price);
	
	// 상품 세부 정보 전체
	public ProductDetailDTO productDetail(String pcode);
	
	// 상품 분류
	public List<CategoryDTO> cateList(String dept1no);
	
	// 중분류 이름
	public CategoryDTO getDept2name(String dept1no, String dept2no);
	
	// 카테고리 목록
	public List<MBTIDTO> mbtiList();
	
	// mbti 상품 목록
	public List<MatchProductDTO> mbtiProdList(String mbti, String price);
	
}
