package com.hyundai.higher.controller.order;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.hyundai.higher.domain.order.OrderSheet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/toss")
@RestController
public class BillingRestController {

	// 토스페이먼츠로 결제 승인 요청.
	@GetMapping("/order")
	public void OrderInfo(@ModelAttribute OrderSheet orderSheet, HttpServletResponse response,
			Principal principal) throws IOException {
		
		
		log.info(orderSheet.toString());

		UUID uuid = UUID.randomUUID();
		String orderId = uuid.toString();
		String customerName = principal.getName();
		
		
		orderSheet.setOrderId(orderId);

		Gson gson = new Gson();

		Map<String, Object> data = new HashMap<>();

		/*
		 * data.put("amount", amount); data.put("orderName", orderName);
		 */
		
		data.put("orderSheet",orderSheet);
		data.put("orderId", orderId);
		data.put("customerName", customerName);
		

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(gson.toJson(orderSheet));


	}

}
