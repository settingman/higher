package com.hyundai.higher.controller.order;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 3. 7.
 * @FileName: BillingController.java
 * @author  : 박성환
 * @설명    : 토스 결제 컨트롤러

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 7.     박성환      	최초 생성
 * </pre>
 */
@Slf4j
@RequestMapping("/toss")
@Controller
public class BillingController {

	private RestTemplate restTemplate = new RestTemplate();
	private static String testSecretApiKey = "test_sk_YPBal2vxj81Rx9BLyw35RQgOAND7";
	private static String tossOriginUrl = "https://api.tosspayments.com/v1/payments/confirm";

	
	
	// 토스페이먼츠로 결제 승인 요청.
	@GetMapping("/success")
	public String requestFinalPayments(@RequestParam String paymentKey, @RequestParam String orderId,
			@RequestParam Long amount) {

		System.out.println("paymentKey = " + paymentKey);
		System.out.println("orderId = " + orderId);
		System.out.println("amount = " + amount);
		
		testSecretApiKey = testSecretApiKey + ":";
	    String encodedAuth = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));
		
		System.out.println("encodedAuth = " + encodedAuth);

		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println("hi = ");
		
		HttpHeaders headers = new HttpHeaders();
		
		System.out.println("hi = ");
		

		headers.set("Authorization", "Basic "+encodedAuth);
		headers.set("Content-Type", "application/json");
		String body = "{\"paymentKey\":\"" + paymentKey + "\",\"amount\":" + amount + ",\"orderId\":\"" + orderId
				+ "\"}";
		System.out.println("body = " + body);

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		String url = "https://api.tosspayments.com/v1/payments/confirm";
		
		
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		// 결제 승인 요청.
		
		
		return "fail";
		// 결제 성공 페이지로 이동.
	
	}

}
