package com.hyundai.higher.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hyundai.higher.domain.order.OrderSheet;

import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 3. 3.
 * @FileName: HomeController.java
 * @author : 박성환
 * @설명 : 메인 페이지.
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 27.     박성환      	최초 생성
 *     </pre>
 */
@Slf4j
@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String memberForm(Model model) {

		log.info("main");

		return "test";
	}

	@GetMapping(value = "/test")
	public String test(Model model) {

		log.info("main");

		return "order/orderComplete";
	}

	@GetMapping(value = "/test3")
	public String test2(Model model) {
		
		List<String> product_id = new ArrayList<>();
		List<Integer> product_Quntity= new ArrayList<>();
		List<String> product_name= new ArrayList<>();
		List<String> product_image= new ArrayList<>();
		List<Integer> product_price= new ArrayList<>();
		
		
		product_id.add("1");
		product_id.add("2");
		
		product_Quntity.add(1);
		product_Quntity.add(2);
		
		product_name.add("1");
		product_name.add("2");
		
		product_image.add("1");
		product_image.add("2");
		
		product_price.add(1);
		product_price.add(2);
		
		OrderSheet orderSheet = new OrderSheet("orderId","oName","oDate",123,"oadd1","oadd2","oreceiver","otel","opay",1234,product_id,product_Quntity,product_name,product_image,product_price) ;
		
		
		model.addAttribute("orderSheet",orderSheet);


		return "test3";
	}

	@GetMapping(value = "/test4")
	public String test4(Model model) {

		return "test4";
	}
	/*
	 * @GetMapping("/success") public String requestFinalPayments(@RequestParam
	 * String paymentKey, @RequestParam String orderId,
	 * 
	 * @RequestParam Long amount) { String url =
	 * "https://api.tosspayments.com/v1/payments/confirm"; String body =
	 * "{\"paymentKey\":\"gqyGWADQ6jf6QMw80VTht\",\"amount\":15000,\"orderId\":\"Rr-6VcLQUxRXIeSXLr7lA\"}";
	 * 
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * headers.set("Authorization",
	 * "Basic dGVzdF9za19ZUEJhbDJ2eGo4MVJ4OUJMeXczNVJRZ09BTkQ3Og==");
	 * 
	 * HttpEntity<String> entity = new HttpEntity<String>(body, headers);
	 * 
	 * ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
	 * entity, String.class);
	 * 
	 * String responseBody = response.getBody();
	 * 
	 * }
	 */
}
