package com.hyundai.higher.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyundai.higher.service.include.IncludeService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 03. 23.
 * @FileName: CartController.java
 * @author : 박서현
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 23.     박서현       최초 생성
 * </pre>
 */

@Log4j2
@RequestMapping("/cart")
@Controller
public class CartController {

	@Autowired(required=true)
	private IncludeService iService;
	
	// 장바구니 페이지
		@GetMapping("/mycart")
		public String cart(Model model) {

			model.addAttribute("categoryList", iService.categoryListAll());
			
			return "cart/mycart";
		}
}
