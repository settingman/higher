package com.hyundai.higher.controller.match;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.service.match.MatchService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 04. 12.
 * @FileName: MatchRestController.java
 * @author : 박서현
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 12.     박서현       최초 생성
 * </pre>
 */

@Log4j2
@RequestMapping("/restMatch")
@RestController
public class MatchRestController {
	
	@Autowired
	private MatchService mService;
	
	@GetMapping("/getOption")
	public String getOption(@RequestParam("pcode") String pcode) {
		log.info("옵션 불러오기");
	
		String result = mService.getOption(pcode);
		if(result == null) {
			result = "";
		}
		log.info(result);
		
		return result+"";
		
	}

	@GetMapping("/getInfo")
	public ResponseEntity<Map<String, Object>> getInfo(@RequestParam("pcode") String pcode) {
		log.info("추가 정보 불러오기");
		
		Map<String, Object> result = new HashMap<>();
		
		MatchProductDTO prod = mService.getInfo(pcode);
		String optname = prod.getOptname() != null ? prod.getOptname() : "";
		result.put("optname", optname);
		result.put("prod", prod);
		
		return ResponseEntity.ok(result);
		
	}
	
	

}
