package com.hyundai.higher.controller.order;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.cart.OrderItem;
import com.hyundai.higher.mapper.member.MemberMapper;
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
 * 2023. 3. 24.     박성환     주문 아이템 리스트 처리
 *     </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shop")
@Controller
public class OrderController {

	private final OrderMapper orderMapper;
	private final MemberMapper memberMapper;

	// 오더페이지 이동.
	@GetMapping(value = "/order")
	public String Order(@RequestParam(required = false, defaultValue = "") List<String> pIdList, Model model,
			Principal principal) {

		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		String mid = principal.getName();

		if (!pIdList.isEmpty()) {

			for (String pcode : pIdList) {

				orderItems.add(orderMapper.selectCartItem(mid, pcode));

			}

		}

		int mileage = memberMapper.findMileage(principal.getName());

		model.addAttribute("mileage", mileage);

		
		model.addAttribute("orderItems", orderItems);
		// 장바구니 객체를 리스트로 담아 넘긴다.

		return "order/order";
	}

}
