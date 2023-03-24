package com.hyundai.higher.mapper.beauty;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.higher.webRTC.dto.ReservationDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 3. 21.
 * @FileName: BeautyMapperTest.java
 * @author  : 박성환
 * @설명    : beauty mapper test

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 21.     박성환      	최초 생성
 * </pre>
 */
@Slf4j
@AutoConfigureMockMvc
@Transactional
public class BeautyMapperTest {

	@Autowired
	BeautyMapper beautyMapper;

	@Test
	public void testfindReservation() {
		List<ReservationDTO> reservations = beautyMapper.findReservation();
		
		log.info(reservations.toString());
	}

}
