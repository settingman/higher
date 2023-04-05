package com.hyundai.higher.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hyundai.higher.mapper.beauty.BeautyMapper;
import com.hyundai.higher.webRTC.dto.ReservationDTO;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class HomeController {
	
	private final BeautyMapper beautyMapper;

	@GetMapping(value = "/")
	public String memberForm(Model model) {

		log.info("main");

		return "main";
	}

	@GetMapping(value = "/test")
	public String test(Model model) {

		log.info("main");

		return "order/orderComplete";
	}

	@GetMapping(value = "/test3")
	public String test2(Model model) {

		/*
		 * CartItem cart = new CartItem("A1504419", "테스트", 10000, "Brand", "Color",
		 * 3,"option", "img_path");
		 * 
		 * CartItem cart2 = new CartItem("A1643072", "테스트2", 20000, "Brand", "Color",
		 * 2,"option", "img_path");
		 */

		/*
		 * carts.add(cart); carts.add(cart2);
		 */

		return "test3";
	}

	@GetMapping(value = "/test4")
	public String test4(Model model) {

		return "test4";
	}

	@GetMapping(value = "/test5")
	public String test5(Model model) {

		List<ReservationDTO> reservations = beautyMapper.findReservation();

		model.addAttribute("reservations", reservations);

		return "admin/reservation";
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
