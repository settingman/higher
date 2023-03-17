package com.hyundai.higher.controller;


import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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

	@GetMapping(value = "/main")
	public String memberForm(Model model) {

		log.info("main");

		return "main";
	}

	@GetMapping(value = "/test")
	public String test(Model model) {

		log.info("main");

		return "test";
	}

	
	@GetMapping(value = "/test3")
	public String test2(Model model) {


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
