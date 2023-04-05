package com.hyundai.higher.controller.match;

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
	public String matchIt(@RequestParam("pcode") String pcode, Model model) {
		log.info("화장품 매칭");
		
		//성분 가져오기
		
		// 10개 성분 처리
		
		//성분 for문 해서 점수 카운트
		return "match/matchIt";
	}
	
	
}
