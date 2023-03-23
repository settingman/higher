package com.hyundai.higher.mapper.skinMBTI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 3. 23.
 * @FileName: SkinMBTIMapperTest.java
 * @author : 신수진
 * @설명 : SkinMBTIMapper Test
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 23.     신수진      최초생성
 *     </pre>
 */
@Log4j2
@SpringBootTest
public class SkinMBTIMapperTest {

	@Autowired
	private SkinMBTIMapper mapper;
	
	@Test
	public void selectMBTITest() {
		
		String stype = "DRNT";
		
		log.info(mapper.selectMBTI(stype));
	}
}
