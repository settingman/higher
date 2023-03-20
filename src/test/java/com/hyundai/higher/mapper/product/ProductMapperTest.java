package com.hyundai.higher.mapper.product;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hyundai.higher.domain.product.CategoryDTO;
import com.hyundai.higher.domain.product.ProductDTO;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 3. 15.
 * @FileName: ProductMapperTest.java
 * @author : 신수진
 * @설명 : ProductMapper Test
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 15.     신수진      최초생성
 *     </pre>
 */
@Log4j2
@SpringBootTest
public class ProductMapperTest {

	@Autowired
	private ProductMapper mapper;
	
	// 카테고리 목록(전체)
	@Test
	public void testCategoryListAll() {
		log.info("test category list all mapper....");
		
		List<CategoryDTO> list = mapper.categoryListAll();
		
		log.info(list);
	}
	
	// 카테고리 목록(하위)
	@Test
	public void testCategoryListSub() {
		log.info("test category list mapper....");
		
		String dept1no = "10";
		
		List<CategoryDTO> list = mapper.categoryListSub(dept1no);
		
		log.info(list);
	}
	
	// 상품 목록
	@Test
	public void testProductList() {
		
		String dept1no = "10";
		String dept2no = "";
		
		List<ProductDTO> list = mapper.productList(dept1no, dept2no);
		
		log.info(list.size());
	}
	
	// 상품 세부 정보
	@Test
	public void testProductDetailInfo() {
		
		String pcode = "A1565069";
		
		log.info(mapper.productDetailInfo(pcode));
	}
	
	// 상품 세부 정보
	@Test
	public void testImgThumbList() {
		
		String pcode = "A1565069";
		
		log.info(mapper.imgThumbList(pcode));
	}
	
	// 상품 세부 정보
	@Test
	public void testImgDetailList() {
		
		String pcode = "A0473095";
		
		log.info(mapper.imgDetailList(pcode));
	}
	
	// 상품 세부 옵션
	@Test
	public void testOptList() {
		
		String pcode = "A0473095";
		
		log.info(mapper.optList(pcode));
	}
	
	
}
