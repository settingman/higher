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

/**
 * @since : 2023. 03. 23.
 * @FileName: CartController.java
 * @author : 박서현
 * @설명 : 장바구니 기능 RestController
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 23.     박서현       최초 생성
 * 2023. 04. 10.	 박서현		장바구니 개수 카운트
 * </pre>
 */

@RestController
@RequestMapping("/restcart")
@Log4j2
public class CartRestController {
	
	@Autowired
	private CartService cService;

	//장바구니에 상품 담기
	@PostMapping(value="/addCart", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE } )
	public String addCart(@RequestBody CartItem cart) {
		
		int result = cService.addCart(cart);
		
		return result + "";
	}
	
	//장바구니 상품 개별 삭제
	@PostMapping(value="/deleteCart", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public String deleteCart (@RequestBody CartItem cart, Principal principal) {
		String mid = principal.getName();
		
		int result = cService.deleteCart(cart.getPcode(), cart.getPoption(), mid);
		
		return result + "";
	}
	
	//장바구니 선택 상품 삭제
	@PostMapping(value="/deleteCarts", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
		      MediaType.APPLICATION_JSON_VALUE })
    public String deleteCarts(@RequestBody List<Map<String, String>> cartList, Principal principal) {

		  String mid = principal.getName();

		  int result =0;
		  for (Map<String, String> cart : cartList) {
	            String pcode = cart.get("pcode");
	            log.info(pcode);
	            String poption = cart.get("poption");
	            result = cService.deleteCart(pcode, poption, mid);


	        }

	        return result+"";

		}

	//장바구니 상품 수량 변경
	@PostMapping(value="/modifyAmount", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public String modifyAmount (@RequestBody CartItem cart, Principal principal) {
		log.info("장바구니 아이템 수량 변경 " + cart.getPcode());
		String mid = principal.getName();
		
		int result = cService.modifyAmount(cart, mid);
		
		return result + "";
	}
	
	//장바구니 아이템 옵션 변경
	@PostMapping(value="/modifyOption", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public String modifyOption (@RequestBody CartItem cart, Principal principal) {
		String mid = principal.getName();
		
		int result = cService.modifyOption(cart, mid);
		
		return result + "";
	}
	
	// 장바구니 개수 카운트
	@GetMapping("/count")
	public String countCart(Principal principal) {
		
		String mid = principal.getName();
		
		int count =0;
		count = cService.getCount(mid);
		
		return count+"";
		
	}
	
	
}

