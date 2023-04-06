package com.hyundai.higher.controller.order;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.hyundai.higher.domain.order.OrderSheet;
import com.hyundai.higher.mapper.member.MemberMapper;
import com.hyundai.higher.mapper.order.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 3. 7.
 * @FileName: BillingController.java
 * @author : 박성환
 * @설명 : 토스 결제 컨트롤러
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 7.     박성환      	최초 생성
 * 2023. 4. 6.     박성환      	마일리지 적립 수정
 *     </pre>
 */
@Slf4j
@RequestMapping("/toss")
@RequiredArgsConstructor
@Controller
public class BillingController {

	private final OrderMapper orderMapper;
	private final MemberMapper memberMapper;
	
	private RestTemplate restTemplate = new RestTemplate();
	private static String testSecretApiKey = "test_sk_YPBal2vxj81Rx9BLyw35RQgOAND7";
	private static String tossOriginUrl = "https://api.tosspayments.com/v1/payments/confirm";

	// 토스페이먼츠로 결제 승인 요청.
	@GetMapping("/success")
	public String requestFinalPayments(@RequestParam String paymentKey, @RequestParam String orderId,
			@RequestParam Long amount, @RequestParam String orderInfo, Model model, Principal principal) {

		log.info("success");
		// 상품 결과 페이지 주문 목록 가지고 이동 가능
		log.info(orderInfo);
		log.info("success");

		Gson gson = new Gson();

		// GSON String 을 ordersheet 객체로 바로 변환.
		OrderSheet orderSheet = gson.fromJson(orderInfo, OrderSheet.class);

		log.info(orderSheet.toString());

		testSecretApiKey = testSecretApiKey + ":";
		String encodedAuth = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));

		System.out.println("encodedAuth = " + encodedAuth);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", "Basic " + encodedAuth);
		headers.set("Content-Type", "application/json");
		String body = "{\"paymentKey\":\"" + paymentKey + "\",\"amount\":" + amount + ",\"orderId\":\"" + orderId
				+ "\"}";

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		String url = "https://api.tosspayments.com/v1/payments/confirm";

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		// 결제 승인 요청.

		model.addAttribute("orderSheet", orderSheet);

		// ORDER TABLE INSERT
		
		String customerName = principal.getName();
		LocalDate now = LocalDate.now();
		
		
		int useMileage = orderSheet.getOMileage();
		int makeMileage = (int) ((int) orderSheet.getOTotalPrice() * 0.03);
		
		int saveMileage = makeMileage-useMileage;
		
		log.info("save mileage {}",saveMileage);
		log.info(" useMileage {}",useMileage);
		log.info(" makeMileage {}",makeMileage);
		
		memberMapper.updateMileage(customerName, saveMileage);

		
		
		

		orderSheet.setODate(now.toString());
		orderMapper.insertOrder(orderSheet, customerName);
		
		int size = orderSheet.getProduct_id().size();
		
		String oId = orderSheet.getOrderId();
		
		for(int i=0; i<size; i++) {
			
			String pCode = orderSheet.getProduct_id().get(i);
			int pAmount =  orderSheet.getProduct_Quntity().get(i);
			
			orderMapper.insertOrderList(oId, pCode, pAmount);
			
			
		}
		
		
		
		// ORDER ITEMS 에 주문된 상품 목록 넣기.
		
		
		
		
		
		
		
		

		return "order/orderComplete";
		// 결제 성공 페이지로 이동 편집.

	}

};