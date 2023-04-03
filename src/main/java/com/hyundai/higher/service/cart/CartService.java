package com.hyundai.higher.service.cart;

import java.util.List;

import com.hyundai.higher.domain.cart.CartItem; 

public interface CartService {
	
		//상품 장바구니 담기
		public int addCart(CartItem cart);
		
		//장바구니 상품 삭제 
		public int deleteCart(String pcode, String optname, String mid);

		//장바구니 상품 선택 삭제 
		//public int deleteCarts(String pcode, String optname, String mid);
		
		//장바구니 상품 수량 변경
		public int modifyAmount(CartItem cart, String mid);

		//장바구니 상품 옵션 변경
		public int modifyOption(CartItem cart, String mid);
		
		//장바구니 상품 목록 불러오기
		public List<CartItem> getCart (String mid);

}
