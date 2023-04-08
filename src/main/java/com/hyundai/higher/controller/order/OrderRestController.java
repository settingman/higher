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
import com.hyundai.higher.domain.order.SearchForm;
import com.hyundai.higher.mapper.order.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 3. 7.
 * @FileName: OrderRestController.java
 * @author : 박성환
 * @설명 : ORDER RESTCONTROLLER
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 7.     박성환      	최초 생성
 *     </pre>
 */
@Slf4j
@RequestMapping("/shop")
@RestController
public class OrderRestController {

	// 토스페이먼츠로 결제 승인 요청.
	@GetMapping("/orderSearch")
	public void OrderSearch(@ModelAttribute SearchForm searchForm, HttpServletResponse response, Principal principal)
			throws IOException {

		log.info(searchForm.toString());

	}

}
