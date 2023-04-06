package com.hyundai.higher.controller.order;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.hyundai.higher.domain.order.OrderSheet;
import com.hyundai.higher.mapper.order.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 3. 7.
 * @FileName: BillingRestController.java
 * @author  : 박성환
 * @설명    : 토스 Api 결제 확인

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 7.     박성환      	최초 생성
 * 
 * </pre>
 */
@Slf4j
@RequestMapping("/toss")
@RestController
public class BillingRestController {

	
	
	
	// 토스페이먼츠로 결제 승인 요청.
	@GetMapping("/order")
	public void OrderInfo(@ModelAttribute OrderSheet orderSheet, HttpServletResponse response, Principal principal)
			throws IOException {

		log.info(orderSheet.toString());

		UUID uuid = UUID.randomUUID();
		String orderId = uuid.toString().substring(0,17);
		String customerName = principal.getName();

		orderSheet.setOrderId(orderId);

		log.info(orderSheet.toString());
		

		Gson gson = new Gson();

		Map<String, Object> data = new HashMap<>();

		/*
		 * data.put("amount", amount); data.put("orderName", orderName);
		 */

		data.put("orderSheet", orderSheet);
		data.put("orderId", orderId);
		data.put("customerName", customerName);
		
		
		
		

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(gson.toJson(orderSheet));

	}

}
