package com.hyundai.higher.controller.match;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.domain.review.ReviewDTO;
import com.hyundai.higher.service.match.MatchService;
import com.hyundai.higher.service.review.ReviewService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 04. 12.
 * @FileName: MatchRestController.java
 * @author : 박서현
 * @설명 : 화장품 매칭하기 기능 관련 RestController
 * 
 * 	   <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 12.     박서현       최초 생성
 * 2023. 04. 13.     박서현       getMatch 추가
 * </pre>
 */

@Log4j2
@RequestMapping("/restMatch")
@RestController
public class MatchRestController {
	
	@Autowired
	private MatchService mService;
	
	@Autowired
	private ReviewService rService;

	//상품 옵션 불러오기
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

	//상품 추가 정보 불러오기
	@GetMapping("/getInfo")
	public ResponseEntity<Map<String, Object>> getInfo(@RequestParam("pcode") String pcode) {
		log.info("추가 정보 불러오기   "+pcode);
		
		Map<String, Object> result = new HashMap<>();
		
		MatchProductDTO prod = mService.getInfo(pcode);
		String optname = prod.getOptname() != null ? prod.getOptname() : "";
		result.put("optname", optname);
		result.put("prod", prod);
		
		List<ReviewDTO> reviewList = rService.reviewList(pcode);
		int rateTotal = 0;
		int rateAvg = 0;
		if(reviewList.size() > 0) {
			for(int i=0; i<reviewList.size(); i++) {
				rateTotal += reviewList.get(i).getRrate()*2;
			}
			rateAvg = rateTotal / reviewList.size();
		}
		log.info("평점  "+ rateAvg);
		result.put("rate", rateAvg);
		result.put("review", reviewList.size());

		
		return ResponseEntity.ok(result);
		
	}
	
	//매칭도 산출
	@PostMapping("/getMatch")
	public String getMatch(@RequestBody Map<String, Object> data, Principal principal) {
	    
	    String pcode = (String) data.get("pcode");

		
		int score = 0;

		if(principal != null) {

			// mbti 가져오기
			String mid = principal.getName();
			String mbti = mService.userMbti(mid);
			if(mbti != null) {

			log.info("mbti--------------  " + mbti);

			// mbti 글자별로 나누기
			String[] mbtiList = mbti.split("");

			// 성분 가져오기
			String[] ingredients = mService.getIngredient(pcode);
			
			// 첫번째 mbti 점수 계산. 총 40점. good 4점, normal 2점, bad 1점
			for (int i = 0; i < ingredients.length; i++) {
				log.info("============================");

				String result = mService.getEffect(ingredients[i], mbtiList[0]);
				log.info("effect " + result);
				if(result == null) {
					score =-1;
					break;
				}
				if (result.equals("GOOD")) {
					log.info("goood");
					score += 4;
				} else if (result.equals("NORMAL")) {
					score += 2;
				}else if(result.equals("BAD")) {
					score += 1;
				}
			}
			log.info(score + "-----------------------------------");
			if(score > 0) {
			
				// 두번째 mbti 점수 계산. 총 30점. good 3점, normal 2점, bad 0점
			for (int i = 0; i < ingredients.length; i++) {
				String result = mService.getEffect(ingredients[i], mbtiList[1]);
				if (result.equals("GOOD")) {
					score += 3;
				} else if (result.equals("NORMAL")) {
					score += 2;
				}
			}

			// 세번째 mbti 점수 계산. 총 20점. good 2점, normal 1점, bad 0점
			for (int i = 0; i < ingredients.length; i++) {
				String result = mService.getEffect(ingredients[i], mbtiList[2]);
				if (result.equals("GOOD")) {
					score += 2;
				} else if (result.equals("NORMAL")) {
					score += 1;
				}
			}

			// 네번째 mbti 점수 계산. 총 10점. good 1점, normal 0점, bad 0점
			for (int i = 0; i < ingredients.length; i++) {
				String result = mService.getEffect(ingredients[i], mbtiList[3]);
				if (result.equals("GOOD")) {
					score += 1;
				}
			}
			log.info("----------------------" + score);
			}
			
		}
			}
		
		return score+"";
		
	}
	
	

}
