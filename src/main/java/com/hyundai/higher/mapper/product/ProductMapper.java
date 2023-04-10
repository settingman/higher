package com.hyundai.higher.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.product.ImgDTO;
import com.hyundai.higher.domain.product.OptionDTO;
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
 * 2023. 3. 15.		신수진		DateBase Product Mapper Interface
 * 2023. 3. 16.		신수진		상품 세부 정보 
 *     </pre>
 */
@Mapper
public interface ProductMapper {
	
	// 상품 목록 - 상품 리스트
	public List<ProductDTO> productList(String dept1no, String dept2no);
	
	// 상품 세부 정보
	public ProductDTO productDetailInfo(String pcode);
	
	// 상품 세부 섬네일 이미지
	public List<ImgDTO> imgThumbList(String pcode);
	
	// 상품 세부 디테일 이미지
	public List<ImgDTO> imgDetailList(String pcode);
	
	// 상품 세부 옵션
	public List<OptionDTO> optList(String pcode);
	
	// 카테고리 목록 이름
	public CategoryDTO getDept2name(String dept1no, String dept2no);
}
