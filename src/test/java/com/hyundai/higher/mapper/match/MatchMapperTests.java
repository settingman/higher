package com.hyundai.higher.mapper.match;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class MatchMapperTests {
	
	@Autowired
	private MatchMapper mapper;
	
	@Test
	public void testGetMemMBTI() {
		String mid = "95parksh@naver.com2";
		
		log.info(mapper.getMemMbti(mid));
	}
}
