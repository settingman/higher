package com.hyundai.higher.controller.cart;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.cart.CartDTO;
import com.hyundai.higher.service.cart.CartService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/restcart")
@Log4j2
public class CartRestController {
	
	@Autowired
	private CartService cService;

	@PostMapping(value = "/addCart", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, String>> addCart(@RequestBody CartDTO cart) {
		log.info("장바구니 ajax 들어옴");
		log.info(cart.getPcode());
		int result =0;
		
		Map<String, String> map = new HashMap<>();
		result = cService.addCart(cart);
		
		if (result ==1) {
			map.put("result", "success");
			
		}
	
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}

