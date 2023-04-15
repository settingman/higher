package com.hyundai.higher.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.domain.review.ReviewDTO;
import com.hyundai.higher.service.product.ProductService;
import com.hyundai.higher.service.review.ReviewService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 03. 06.
 * @FileName: ProductController.java
 * @author : 신수진
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 06.    신수진		최초 생성
 * 2023. 03. 16.	신수진		카테고리, 상품 목록
 * 2023. 03. 17.	신수진		상품 세부 
 * 2023. 04. 10.	신수진		왼쪽 카테고리 목록 수정
 * 2023. 04. 15.	신수진		상품별 리뷰 조회 기능 추가
 * </pre>
 */

@Log4j2
@RequestMapping("/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private ReviewService rService;
	
	// 상품 목록 페이지
	@GetMapping("/list")
	public String productList(@RequestParam(value="dept1", required=false, defaultValue = "") String dept1no, 
							@RequestParam(value="dept2", required=false, defaultValue = "") String dept2no, 
							@RequestParam(value="mbti", required=false, defaultValue = "") String mbti, Model model) {
		
		if(dept1no.equals("10")) {
			model.addAttribute("dept1name", "스킨케어");
		}else if(dept1no.equals("20")) {
			model.addAttribute("dept1name", "메이크업");
		}else if(dept1no.equals("30")) {
			model.addAttribute("dept1name", "바디/헤어케어");
		}else {
			model.addAttribute("dept1name", "MBTI 별");
		}
		
		model.addAttribute("dept1no", dept1no);
		model.addAttribute("dept2no", dept2no);
		if(!dept2no.equals("")) {
			model.addAttribute("dept2name", service.getDept2name(dept1no, dept2no).getDept2name());
		}
		model.addAttribute("cateList", service.cateList(dept1no));
		model.addAttribute("mbtiList", service.mbtiList());
		
		if(mbti.equals("")) {
			model.addAttribute("productList", service.productList(dept1no, dept2no));
		}else {
			model.addAttribute("mbtiProdList", service.mbtiProdList(mbti));
		}
		
		model.addAttribute("mbti", mbti);
		
		return "product/list";
	}
	
	// 상품 세부 페이지
	@GetMapping("/detail")
	public String productDetail(@RequestParam("code") String pcode, Model model) {
				
		ProductDetailDTO dto = new ProductDetailDTO();
		dto = service.productDetail(pcode);
		int dept1no = dto.getProductDTO().getDept1no();
		
		model.addAttribute("dept1no", dept1no);
		model.addAttribute("product", service.productDetail(pcode));
		
		List<ReviewDTO> reviewList = rService.reviewList(pcode);
		
		int rateTotal = 0;
		int rateAvg = 0;
		if(reviewList.size() > 0) {
			for(int i=0; i<reviewList.size(); i++) {
				rateTotal += reviewList.get(i).getRrate()*2;
			}
			rateAvg = rateTotal / reviewList.size();
		}
		
		// 리뷰
		model.addAttribute("reviewList", reviewList);
		// 리뷰 평점
		model.addAttribute("rateAvg", rateAvg);
		
		return "product/detail";
	}

	
	
	
}
