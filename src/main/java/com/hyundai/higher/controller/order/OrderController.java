package com.hyundai.higher.controller.order;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.cart.CartItem;
import com.hyundai.higher.domain.cart.OrderItem;
import com.hyundai.higher.mapper.order.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 3. 3.
 * @FileName: OrderController.java
 * @author : 박성환
 * @설명 : 오더 서비스 구현.
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 3.     박성환      최초 생성
 * 2023. 3. 6.     박성환      주문 목록 페이지 처리
 *     </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shop")
@Controller
public class OrderController {

	private final OrderMapper orderMapper;
	
	
	// 오더페이지 이동.
	@GetMapping(value = "/order")
	public String Order(@RequestParam(required = false,defaultValue = "") List<String> pIdList, Model model, Principal principal) {

		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		
		String mid = principal.getName();
			
		
		if(!pIdList.isEmpty()) {
			
			for(String pcode : pIdList) {
				
				
				
				orderItems.add(orderMapper.selectCartItem(mid, pcode));
				
			}
			
			
		}
		
		
		

		OrderItem cart = new OrderItem("test", "테스트", 10000, "Brand", "Color", 3,"option", "img_path");
		
		OrderItem cart2 = new OrderItem("test2", "테스트2", 20000, "Brand", "Color", 2,"option", "img_path");
		// cartService.cartToOrder(pIdList,principal.getName());
		// 장바구니에서 선택된 상품의 id 값을 리스트로 받은 뒤 상품id와 회원 id를 통하여 장바구니 정보를 가져와 담아준다.

		
		orderItems.add(cart);
		orderItems.add(cart2);
		// 장바구니에서 상품id를 넘겨받아서 그걸 사용해서 카트테이블 조회해서 카트 객체 받아서 뿌림.
		model.addAttribute("orderItems", orderItems);
		// 장바구니 객체를 리스트로 담아 넘긴다.

		return "order/order";
	}

}
