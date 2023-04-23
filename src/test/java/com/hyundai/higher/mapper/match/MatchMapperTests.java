package com.hyundai.higher.mapper.match;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hyundai.higher.domain.match.MatchProductDTO;

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
	
	@Test
	public void testGetIngredient() {
		String pcode = "A1492372";
		log.info(mapper.getIngredient(pcode));
	}
	
	@Test
	public void testGetEffect() {
		String ingredient = "시어버터";
		String mbti = "D";
		log.info(mapper.getEffect(ingredient, mbti));
	}
	
	@Test
	public void testGetMBTIProduct() {
		String pmbti = "DNST";
		
		List<MatchProductDTO> list = mapper.mbtiProduct(null, pmbti,null);
		
		list.forEach(p -> log.info(p));
	}
}
