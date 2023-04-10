package com.hyundai.higher.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.service.product.ProductService;

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
 * </pre>
 */

@Log4j2
@RequestMapping("/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	// 상품 목록 페이지
	@GetMapping("/list")
	public String productList(@RequestParam("dept1") String dept1no, @RequestParam(value="dept2", required=false, defaultValue = "") String dept2no, Model model) {
		
		if(dept1no.equals("10")) {
			model.addAttribute("dept1name", "스킨케어");
		}else if(dept1no.equals("20")) {
			model.addAttribute("dept1name", "메이크업");
		}else if(dept1no.equals("30")) {
			model.addAttribute("dept1name", "바디/헤어케어");
		}
		
		model.addAttribute("dept1no", dept1no);
		model.addAttribute("dept2no", dept2no);
		if(!dept2no.equals("")) {
			model.addAttribute("dept2name", service.getDept2name(dept1no, dept2no).getDept2name());
		}
		model.addAttribute("cateList", service.cateList(dept1no));
		model.addAttribute("productList", service.productList(dept1no, dept2no));
		
		return "product/list";
	}
	
	// 상품 세부 페이지
	@GetMapping("/detail")
	public String productDetail(@RequestParam("code") String pcode, Model model) {
				
		ProductDetailDTO dto = new ProductDetailDTO();
		dto = service.productDetail(pcode);
		int dept1no = dto.getProductDTO().getDept1no();
		
		log.info(dto);
		
		model.addAttribute("dept1no", dept1no);
		model.addAttribute("product", service.productDetail(pcode));
		
		return "product/detail";
	}

	
	
	
}
