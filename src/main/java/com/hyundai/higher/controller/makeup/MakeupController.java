package com.hyundai.higher.controller.makeup;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 3. 21.
 * @FileName: MakeupController.java
 * @author : 이세아
 * @설명 : 색조 화장 관련 기능 컨트롤러
 * 
 *     <pre>
 * 수정일           수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 21.     이세아       create
 * 2023. 3. 21.     이세아       html 연결 및 makeup api 연동
 *     </pre>
 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/makeup")
@Controller
public class MakeupController {
	
	@GetMapping("/reserv_main")
	public void reserv_main() {
		log.info("==== Make-on 메인 페이지====");
	}

	@GetMapping("/reserv_type")
	public void reserv_type() {
		log.info("==== Make-on 메인 페이지====");
	}
	
	@GetMapping("/reserv_offline")
	public void reserv_offline() {
		log.info("==== 예약 디테일 입력 페이지 : 오프라인 전용 ====");
	}

	@GetMapping("/reserv_online")
	public void reserv_online() {
		log.info("==== 예약 디테일 입력 페이지 : 온라인 전용 ====");
	}

	// Flask api 연결 코드 -> 파이썬 세팅된 컴퓨터만 가능
	@PostMapping("/makeup.api")
	public String MakeupApi(@RequestParam("filePath") String filePath,
	        @RequestParam("choice") String choice,@RequestParam("color") String color,Model model) throws IOException {
	    
		log.info("선택한 메이크업 방식 : " + choice);
		log.info("선택된 색상 : " + color);
	    log.info(filePath);

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    map.add("filePath", filePath);
	    map.add("choice", choice);
	    map.add("color", color);

	    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

	    String responseString = restTemplate.postForObject("http://127.0.0.1:5000/apply-makeup/", requestEntity, String.class);

	    model.addAttribute("file_path", responseString);
	    return "makeup-result";
	}

}
