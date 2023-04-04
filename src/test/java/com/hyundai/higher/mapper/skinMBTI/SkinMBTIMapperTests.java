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
public class SkinMBTIMapperTests {

	@Autowired
	private SkinMBTIMapper mapper;
	
	// 피부타입별 설명 조회
	@Test
	public void testSelectMBTI() {
		
		String stype = "DRNT";
		
		log.info(mapper.selectMBTI(stype));
	}
	
	// 회원 피부타입 진단 결과 저장
	@Test
	public void testUpdateMBTI() {
		
		String mid = "angz";
		String mbti = "DRNT";
		String mbti_scores = "9,9,9,9";
		
<<<<<<< HEAD:src/test/java/com/hyundai/higher/mapper/skinMBTI/SkinMBTIMapperTests.java
		mapper.updateMBTI(mid, mbti, mbti_scores);
=======
		/* mapper.updateMBTI(mid, mbti); */
>>>>>>> 71b06187c7dd27efabaa418055c13015cddcbe2f:src/test/java/com/hyundai/higher/mapper/skinMBTI/SkinMBTIMapperTest.java
	}
	
	
}
