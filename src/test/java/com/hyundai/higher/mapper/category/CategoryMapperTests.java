package com.hyundai.higher.mapper.category;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 4. 4.
 * @FileName: CategoryMapperTests.java
 * @author : 신수진
 * @설명 : CategoryMapper Test
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 4.     신수진      최초생성
 *     </pre>
 */
@Log4j2
@SpringBootTest
public class CategoryMapperTests {
	
	@Autowired
	private CategoryMapper mapper;
	
	// 카테고리 중분류 전체 목록
	@Test
	public void testCateListAll() {
		String dept1no = "10";
		
		log.info(mapper.cateListAll(dept1no));
		
	}
	
	// mbti 전체 목록
	@Test
	public void testMbtiCateListAll() {
		
		log.info(mapper.mbtiCateListAll());
	}

	// 브랜드 전체 목록
	@Test
	public void testBrandCateListAll() {
		
		String dept1no = "10";
		
		log.info(mapper.brandCateListAll(dept1no));
	}
	
}
