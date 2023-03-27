//package com.hyundai.higher.mapper.product;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.hyundai.higher.TestConfig;
//import com.hyundai.higher.domain.product.CategoryDTO;
//import com.hyundai.higher.domain.product.ProductDTO;
//import com.hyundai.higher.mapper.member.MemberMapper;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @since : 2023. 3. 15.
// * @FileName: ProductMapperTest.java
// * @author : 신수진
// * @설명 : ProductMapper Test
// * 
// *     <pre>
// *   수정일         수정자               수정내용
// * ----------      --------    ---------------------------
// * 2023. 03. 15.     신수진      최초생성
// *     </pre>
// */
//@Slf4j
//@RunWith(SpringRunner.class)
//@EnableAutoConfiguration
//@SpringBootTest(classes= {TestConfig.class })
//public class ProductMapperTest {
//
//	@Autowired
//	private ProductMapper mapper;
//	
//	// 카테고리 목록(전체)
//	@Test
//	public void testCategoryListAll() {
//		log.info("test category list all mapper....");
//		
//		List<CategoryDTO> list = mapper.categoryListAll();
//		
//		log.info(list.toString());
//	}
//	
//	// 카테고리 목록(하위)
//	@Test
//	public void testCategoryListSub() {
//		log.info("test category list mapper....");
//		
//		String dept1no = "10";
//		
//		List<CategoryDTO> list = mapper.categoryListSub(dept1no);
//		
//		log.info(list.toString());
//	}
//	
//	// 상품 목록
//	@Test
//	public void testProductList() {
//		
//		String dept1no = "10";
//		String dept2no = "";
//		
//		List<ProductDTO> list = mapper.productList(dept1no, dept2no);
//		
//	}
//	
//}
