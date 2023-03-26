package com.hyundai.higher.controller.makeup;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.service.makeup.MakeupService;

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
 * 2023. 3. 23.		이세아	   makeup api 색상 선택별로 변경 가능하게 함
 * 2023. 3. 25.		이세아	   ajax를 위한 json 타입으로 return 변경, ajax done
 * 2023. 3. 26. 	이세아	   makeup result를 위한 controller 추가
 * 2023. 3. 27.		이세아	   makeup service을 통한 결과값 불러오기 완료
 *     </pre>
 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/makeup")
@Controller
public class MakeupController {
	
	@Autowired
	private MakeupService service;

	@GetMapping("/reserv_main")
	public void reserv_main() {
		log.info("==== Make-on 메인 페이지====");
	}

	@GetMapping("/reserv_type")
	public void reserv_type() {
		log.info("==== Make-on 예약 타입 선정 ====");
	}

	@GetMapping("/reserv_offline")
	public void reserv_offline() {
		log.info("==== 예약 디테일 입력 페이지 : 오프라인 전용 ====");
	}

	@GetMapping("/reserv_online")
	public void reserv_online() {
		log.info("==== 예약 디테일 입력 페이지 : 온라인 전용 ====");
	}
	
	@GetMapping("/makeupform")
	public String makeupForm() {
		log.info("====== 플라스크 연동 메이크업 시연 ======");
		return "makeupform";
	}

	// 결과 DB 연동 코드 -> result 보내기
	// Flask api 연결 코드 -> 파이썬 세팅된 컴퓨터만 가능
	@PostMapping("/makeupresult")
	public String MakeupResult(@RequestParam("filePath") String filePath, @RequestParam("lips") String lips,
			@RequestParam("blush") String blush, @RequestParam("foundation") String foundation, Model model)
			throws IOException {

		log.info("선택한 입술 색상 : " + lips);
		log.info("선택된 블러쉬 색상 : " + blush);
		log.info("선택된 파운데이션 색상 : " + foundation);
		log.info("원본 파일 경로 : " + filePath);

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("filePath", filePath);
		map.add("lips", lips);
		map.add("blush", blush);
		map.add("foundation", foundation);

		String apiUrl = "http://127.0.0.1:5000/apply-makeup/";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		String responseJson = restTemplate.postForObject(apiUrl, request, String.class);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> responseData = mapper.readValue(responseJson, new TypeReference<Map<String, String>>() {
		});

		model.addAttribute("lips", responseData.get("lips"));
		model.addAttribute("blush", responseData.get("blush"));
		model.addAttribute("foundation", responseData.get("foundation"));
		model.addAttribute("output_filepath", responseData.get("output_filepath"));

		log.info(responseData);
		
		List<LipVO> liplist = service.pickLip(lips);
		List<BlushVO> blushlist = service.pickBlush(blush);
		List<FoundationVO> foundationlist = service.pickFoundation(foundation);
		
		model.addAttribute("liplist", liplist);
		model.addAttribute("blushlist", blushlist);
		model.addAttribute("foundationlist", foundationlist);

		return "makeup_result";
	}

	// ajax용 코드
	// Flask api 연결 코드 -> 파이썬 세팅된 컴퓨터만 가능
	@PostMapping("/makeup.api")
	@ResponseBody
	public ResponseEntity<String> MakeupApi(@RequestParam("filePath") String filePath,
			@RequestParam("lips") String lips, @RequestParam("blush") String blush,
			@RequestParam("foundation") String foundation, Model model) throws IOException {

		log.info("선택한 입술 색상 : " + lips);
		log.info("선택된 블러쉬 색상 : " + blush);
		log.info("선택된 파운데이션 색상 : " + foundation);
		log.info("원본 파일 경로 : " + filePath);

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("filePath", filePath);
		map.add("lips", lips);
		map.add("blush", blush);
		map.add("foundation", foundation);

		String apiUrl = "http://127.0.0.1:5000/apply-makeup/";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		String responseJson = restTemplate.postForObject(apiUrl, request, String.class);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> responseData = mapper.readValue(responseJson, new TypeReference<Map<String, String>>() {
		});

		model.addAttribute("lips", responseData.get("lips"));
		model.addAttribute("blush", responseData.get("blush"));
		model.addAttribute("foundation", responseData.get("foundation"));
		model.addAttribute("output_filepath", responseData.get("output_filepath"));

		log.info(responseData);

		//ajax 처리를 위한 json 추가
		try {
			String json = mapper.writeValueAsString(responseData);
			return ResponseEntity.ok(json);
		} catch (JsonProcessingException e) {
			log.error("변환이 안된다 변환이 안된다!!", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
