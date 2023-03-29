package com.hyundai.higher.mapper.similarCos;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class SimilarCosMapperTest {

	@Autowired
	private SimilarCosMapper mapper;
	
	@Test
	public void testCntList() {
		
		log.info("start....");
		
		String pcode = "A1340571";
		int dept1no = 10;
		int dept2no = 12;
		String tmp = "갈락토미세스발효여과물, 정제수, 부틸렌글라이콜, 글리세린, 펜틸렌글라이콜, 소듐하이알루로네이트, 메틸파라벤, 소듐벤조에이트";
		String[] iList = tmp.split(", ");
		List<String> list = new ArrayList<>();
		for(String i : iList) {
			list.add(i);
		}
		
		log.info(list);
		
		log.info("result -> " + mapper.cntList(pcode, dept1no, dept2no, list));
		 
	}
}
