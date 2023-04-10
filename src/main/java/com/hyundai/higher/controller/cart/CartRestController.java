package com.hyundai.higher.controller.cart;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.cart.CartItem;
import com.hyundai.higher.service.cart.CartService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/restcart")
@Log4j2
public class CartRestController {
	
	@Autowired
	private CartService cService;

	@PostMapping(value="/addCart", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE } )
	public String addCart(@RequestBody CartItem cart) {
		log.info("장바구니 ajax 들어옴");
		
		int result = cService.addCart(cart);
		
		return result + "";
	}
	
	@PostMapping(value="/deleteCart", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public String deleteCart (@RequestBody CartItem cart, Principal principal) {
		log.info("장바구니 아이템 삭제 " + cart.getPcode());
		String mid = principal.getName();
		
		int result = cService.deleteCart(cart.getPcode(), cart.getPoption(), mid);
		
		return result + "";
	}
	
	@PostMapping(value="/deleteCarts", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
		      MediaType.APPLICATION_JSON_VALUE })
    public String deleteCarts(@RequestBody List<Map<String, String>> cartList, Principal principal) {

//		public String deleteCarts (@RequestBody List<Map<String, String>> arr, Principal principal) {
		  log.info("장바구니 선택 삭제 ");
		  String mid = principal.getName();

		  int result =0;
		  for (Map<String, String> cart : cartList) {
	            String pcode = cart.get("pcode");
	            log.info(pcode);
	            String poption = cart.get("poption");
	            result = cService.deleteCart(pcode, poption, mid);

	            // 장바구니에서 상품 삭제 등의 비즈니스 로직 처리
	            // ...
	        }
//		  for (Map<String, String> item : arr) {
//			  System.out.println("------------------");
//			  log.info(item.get("pcode"));
//		    String pcode = item.get("pcode");
//		    String poption = item.get("poption");
//		    cService.deleteCart(pcode, poption, mid);
//		  }
		  //int result = cService.deleteCart(cart.getPcode(), cart.getPoption(), mid);

//		  int result = 0;
//		  return result + "";
	        return result+"";

		}

	
	@PostMapping(value="/modifyAmount", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public String modifyAmount (@RequestBody CartItem cart, Principal principal) {
		log.info("장바구니 아이템 수량 변경 " + cart.getPcode());
		String mid = principal.getName();
		
		int result = cService.modifyAmount(cart, mid);
		
		return result + "";
	}
	
	@PostMapping(value="/modifyOption", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public String modifyOption (@RequestBody CartItem cart, Principal principal) {
		log.info("장바구니 아이템 옵션 변경 " + cart.getPcode());
		String mid = principal.getName();
		
		int result = cService.modifyOption(cart, mid);
		
		return result + "";
	}
	
	// 장바구니 개수 카운트
	@GetMapping("/count")
	public String countCart(Principal principal) {
		log.info("장바구니 카운트");
		
		String mid = principal.getName();
		
		int count =0;
		count = cService.getCount(mid);
		log.info("장바구니 개수 "+count);
		
		return count+"";
		
	}
	
	
//	@PostMapping(value = "/addCart", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<Map<String, String>> addCart(@RequestBody CartItem cart) {
//		log.info("장바구니 ajax 들어옴");
//		
//		log.info(cart.toString());
//		
//		int result =0;
//		if()
//		
//		Map<String, String> map = new HashMap<>();
//		result = cService.addCart(cart);
//		
//		if (result ==0) {
//			map.put("result", "success");
//			
//		}
//	
//		return new ResponseEntity<>(map, HttpStatus.OK);
//	}
	
	
}

