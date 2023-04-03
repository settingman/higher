package com.hyundai.higher.mapper.makeup;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundai.higher.TestConfig;
import com.hyundai.higher.domain.makeup.ReservVO;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 3. 27.
 * @FileName: ReservMapperTest.java
 * @author : 이세아
 * @설명 : @
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 27.     이세아       create
 *     </pre>
 */

@Log4j2
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = { TestConfig.class })
public class ReservMapperTest {

	@Autowired
	private ReservMapper mapper;

	@Test
	public void CountDate() {
		String rdate = "2022-03-30 11:00:00";
		mapper.CountDate(rdate);
		log.info(mapper.CountDate(rdate));
	}

	@Test
	public void insertreserv() {
		ReservVO vo = new ReservVO();
		vo.setRimg("https://dispatch.cdnser.be/wp-content/uploads/2017/02/be247503d597fc4b0c5c814ffd68a534.jpg");
		vo.setRdate("2022-03-31 12:00:00");
		vo.setMid("angz");
		log.info(vo);
	}

}
