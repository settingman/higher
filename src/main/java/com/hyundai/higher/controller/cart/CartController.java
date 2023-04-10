package com.hyundai.higher.controller.cart;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyundai.higher.domain.cart.CartItem;
import com.hyundai.higher.service.cart.CartService;

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
 * 2023. 04. 10.	 박서현		장바구니 카운트
 * </pre>
 */

@Log4j2
@RequestMapping("/shop")
@Controller
public class CartController {

	@Autowired(required=true)
	private CartService cService;
	
	
	
	// 장바구니 페이지로 이동
	@GetMapping("/mycart")
	public String cart(Model model, Principal principal) {
		log.info("장바구니 이동");
		
		String mid = principal.getName();
		log.info("-----");
		List<CartItem> carts = cService.getCart(mid);
		log.info("-------"+carts.isEmpty());
		if(!carts.isEmpty()) {
			model.addAttribute("carts", carts);
			
		}
		log.info(carts);

			
	return "shop/mycart";
	}
	

	
}
