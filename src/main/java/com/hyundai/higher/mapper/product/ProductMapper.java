package com.hyundai.higher.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.product.CategoryDTO;
import com.hyundai.higher.domain.product.ProductDTO;

/**
 * @since : 2023. 3. 15.
 * @FileName: ProductMapper.java
 * @author : 신수진
 * @설명 : 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     신수진      DateBase Product Mapper Interface
 *     </pre>
 */
@Mapper
public interface ProductMapper {
	
	// 카테고리 목록
	public List<CategoryDTO> categoryListAll();
	
	// 상품 목록 - 카테고리
	public List<CategoryDTO> categoryListSub(String dept1no);
	
	// 상품 목록 - 상품 리스트
	public List<ProductDTO> productList(String dept1no, String dept2no);
	
}
