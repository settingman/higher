package com.hyundai.higher.controller.match;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.service.match.MatchService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 04. 01.
 * @FileName: MatchController.java
 * @author : 박서현
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 01.     박서현       최초 생성
 * </pre>
 */

@Log4j2
@RequestMapping("/match")
@Controller
public class MatchController {
	
	@Autowired(required=true)
	private MatchService mService;
	
	//화장품 매칭 메인 페이지
	@GetMapping("/matchMain")
	public String matchMain(@RequestParam("mbti") String mbti, Model model) {
		log.info("화장품 매칭 이동");
		//model.addAttribute("categoryList", iService.categoryListAll());
		
		List<MatchProductDTO> product = mService.mbtiProduct(mbti);
		model.addAttribute("product",product);
		
//		MatchMbtiDTO dto = new MatchMbtiDTO();
//		dto = mService.getMbtiInfo(pmbti);
		model.addAttribute("mbtiInfo",mService.getMbtiInfo(mbti));

	 return "match/matchMain";
	}
	
	//화장품 검색 
	@GetMapping("/searchProduct")
	public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
		log.info("화장품 검색: "+keyword);
	
		List<MatchProductDTO> product = mService.searchProduct(keyword);
		model.addAttribute("product",product);
		
		return "match/searchProduct";
		
	}
	
	//화장품 매칭하기
	@GetMapping("/matchIt")
	public String matchIt(@RequestParam("pcode") String pcode, Model model, Principal principal) {
		log.info("화장품 매칭");
		
		//mbti 가져오기
		String mid = principal.getName();
		String mbti = mService.userMbti(mid);
		log.info("mbti--------------  "+mbti);
		
		//mbti 글자별로 나누기
		String[] mbtiList  = mbti.split("");

		//성분 가져오기
		String[] ingredients = mService.getIngredient(pcode);
	

		//성분 for문 해서 점수 카운트
		int score = 0;
		//첫번째 mbti 점수 계산. 총 40점. good 4점, normal 2점, bad 0점 
		for(int i=0; i<ingredients.length;i++) {
			log.info("============================");

			String result = mService.getEffect(ingredients[i], mbtiList[0]);
			log.info("effect "+result);
			if(result.equals("GOOD")) {
				log.info("goood");
				score +=4;
			}else if(result.equals("NORMAL")) {
				score +=2;
			}
		}
		log.info(score+"-----------------------------------");
		
		//두번째 mbti 점수 계산. 총 30점. good 3점, normal 2점, bad 0점
		for(int i=0; i<ingredients.length;i++) {
			String result = mService.getEffect(ingredients[i], mbtiList[1]);
			if(result.equals("GOOD")) {
				score +=3;
			}else if(result.equals("NORMAL")) {
				score +=2;
			}
		}
		
		//세번째 mbti 점수 계산. 총 20점. good 2점, normal 1점, bad 0점
		for(int i=0; i<ingredients.length;i++) {
			String result = mService.getEffect(ingredients[i], mbtiList[2]);
			if(result.equals("GOOD")) {
				score +=2;
			}else if(result.equals("NORMAL")) {
				score +=1;
			}
		}
		
		//네번째 mbti 점수 계산. 총 10점. good 1점, normal 0점, bad 0점
		for(int i=0; i<ingredients.length;i++) {
			String result = mService.getEffect(ingredients[i], mbtiList[3]);
			if(result.equals("GOOD")) {
				score +=1;
			}
		}
		log.info("----------------------"+score);
		model.addAttribute("score",score);
		
		return "match/matchIt";
	}
	
	
}
