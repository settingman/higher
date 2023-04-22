package com.hyundai.higher.controller.match;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.domain.match.MemberMBTIDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.domain.review.ReviewDTO;
import com.hyundai.higher.security.dto.SecurityMember;
import com.hyundai.higher.service.match.MatchService;
import com.hyundai.higher.service.product.ProductService;
import com.hyundai.higher.service.review.ReviewService;
import com.hyundai.higher.service.similarCos.SimilarCosService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 04. 01.
 * @FileName: MatchController.java
 * @author : 박서현, 신수진
 * @설명 : 화장품 매칭하기 기능 관련 컨트롤러
 * 
 *     <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 01.    박서현       최초 생성 (main)
 * 2023. 04. 06.	신수진		main, match/detail 유사성분템 추천
 * 2023. 04. 07.	박서현		main, match/detail
 *     </pre>
 */

@Log4j2
@RequestMapping("/match")
@Controller
public class MatchController {

	@Autowired(required = true)
	private MatchService mService;

	@Autowired
	private ProductService pService;

	@Autowired
	private SimilarCosService sService;

	@Autowired
	private ReviewService rService;
	
	// 화장품 매칭 메인 페이지
	@GetMapping("/main")
	public String mathMain2(@RequestParam(value = "cno", required = false, defaultValue = "") String dept2no,
						@RequestParam(value = "mbti", required = false, defaultValue = "") String mbti, Model model,
			Principal prin, @AuthenticationPrincipal SecurityMember securityMember) {

		if (prin != null) {
			String mid = prin.getName();
			MemberMBTIDTO memMbti = mService.getMemMBTI(mid);
			model.addAttribute("memMBTI", memMbti);

			if (memMbti != null) {
				StringTokenizer st = new StringTokenizer(memMbti.getMbti_scores(), ",");
				List<Integer> scores = new ArrayList<>();
				for (int i = 0; i < 4; i++) {
					scores.add(Integer.parseInt(st.nextToken()));
				}
				model.addAttribute("scores", scores);
			}
		}

		List<MatchProductDTO> product = mService.mbtiProduct(dept2no, mbti);

		model.addAttribute("dept2no", dept2no);
		model.addAttribute("product", product);
		if(securityMember != null) {
			model.addAttribute("mbti", securityMember.getMbti());
		}else {
			model.addAttribute("mbti", "");
		}

		model.addAttribute("cateMap", mService.cateMap());

		return "match/main";
	}
	
	// 매칭 세부
	@GetMapping("/detail")
	public String matchDetail(@RequestParam("pcode") String pcode, Model model, Principal principal) {
		log.info("pcode :  " + pcode);

		ProductDetailDTO product = pService.productDetail(pcode);
		String ingredient = product.getProductDTO().getPingredient();
		ingredient = ingredient.replaceAll(",", " ").replaceAll("\\s+", " ");
		
		String[] iList = ingredient.split(" ");
		String[] mainIngredient = new String[6];
		
		List<ReviewDTO> reviewList = rService.reviewList(pcode);
				
		int rateTotal = 0;
		int rateAvg = 0;
		if(reviewList.size() > 0) {
			for(int i=0; i<reviewList.size(); i++) {
				rateTotal += reviewList.get(i).getRrate()*2;
			}
			rateAvg = rateTotal / reviewList.size();
		}

		// 리뷰 평점
		model.addAttribute("rateAvg", rateAvg);
		model.addAttribute("rcnt", reviewList.size());

		int idx = 0;

		for (String i : iList) {
			i = i.trim();
			if (i.equals("정제수"))
				continue;
			if (i.equals("글리세린"))
				continue;

			mainIngredient[idx] = i;
			idx++;
			if (idx == 6)
				break;
		}

		// 성분
		model.addAttribute("product", product);
		model.addAttribute("mainIngredient", mainIngredient);
		model.addAttribute("similarCos", sService.recogProducts(pcode));

		if(principal != null) {
			int score = 0;


		// mbti 가져오기
		String mid = principal.getName();
		String mbti = mService.userMbti(mid);
		if(mbti != null) {

		log.info("mbti--------------  " + mbti);

		// mbti 글자별로 나누기
		String[] mbtiList = mbti.split("");

		// 성분 가져오기
		String[] ingredients = mService.getIngredient(pcode);
		
		Set<String> set = new HashSet<>();
		Set<String> badset = new HashSet<>();

		// 첫번째 mbti 점수 계산. 총 40점. good 4점, normal 2점, bad 1점
		log.info(mbtiList[0]);
		for (int i = 0; i < ingredients.length; i++) {
			log.info("============================"+ingredients[i]);

			String result = mService.getEffect(ingredients[i], mbtiList[0]);
			log.info("effect " + result);
			if(result == null) {
				score =-1;
				break;
			}
			
			//성분 효과 가져오기
			String info = mService.getIinfo(ingredients[i], mbtiList[0]);
			String[] infoArray = info.split("\\s*,\\s*");
			for (String s : infoArray) {
			    set.add(s.trim());
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
			if(score != 0) {
				log.info("두번째 진입"+mbtiList[1]);
				
			
			// 두번째 mbti 점수 계산. 총 30점. good 3점, normal 2점, bad 0점
			for (int i = 0; i < ingredients.length; i++) {
				log.info("============================"+ingredients[i]);
	
				String result = mService.getEffect(ingredients[i], mbtiList[1]);
				log.info("effect " + result);
	
				if(result == null) {
					score =-1;
					break;
				}
				String info = mService.getIinfo(ingredients[i], mbtiList[0]);
				String[] infoArray = info.split("\\s*,\\s*");
				for (String s : infoArray) {
				    set.add(s.trim());
				}
				
				if (result.equals("GOOD")) {
					score += 3;
				} else if (result.equals("NORMAL")) {
					score += 2;
				}
			}
	
			log.info("세번째 진입"+mbtiList[2]);
	
			// 세번째 mbti 점수 계산. 총 20점. good 2점, normal 1점, bad 0점
			for (int i = 0; i < ingredients.length; i++) {
				String result = mService.getEffect(ingredients[i], mbtiList[2]);
				log.info("============================"+ingredients[i] + "----------");
				log.info("effect " + result);
	
	
				if(result == null) {
					score =-1;
					break;
				}
				String info = mService.getIinfo(ingredients[i], mbtiList[0]);
				String[] infoArray = info.split("\\s*,\\s*");
				for (String s : infoArray) {
				    set.add(s.trim());
				}
				
				if (result.equals("GOOD")) {
					score += 2;
				} else if (result.equals("NORMAL")) {
					score += 1;
				}
			}
	
			log.info("네번째 진입"+mbtiList[3] + "  점수  "+ score);
	
			// 네번째 mbti 점수 계산. 총 10점. good 1점, normal 0점, bad 0점
			for (int i = 0; i < ingredients.length; i++) {
				String result = mService.getEffect(ingredients[i], mbtiList[3]);
				log.info("============================"+ingredients[i]);
				log.info("effect " + result);
	
				if(result == null) {
					score =-1;
					break;
				}
				String info = mService.getIinfo(ingredients[i], mbtiList[0]);
				String[] infoArray = info.split("\\s*,\\s*");
				for (String s : infoArray) {
				    set.add(s.trim());
				}
				
				if (result.equals("GOOD")) {
					score += 1;
				}
			}
			log.info("----------------------" + score);
			
			List<String> infolist = new ArrayList<>(set);
			model.addAttribute("infolist",infolist);
			}
		}
		model.addAttribute("score", score);

	}
		
		//}

		return "match/detail";
	}

}
