package com.hyundai.higher.service.cart;

import com.hyundai.higher.domain.cart.CartDTO; 

public interface CartService {
	
		//상품 장바구니 담기
		public int addCart(CartDTO cart);
		
		//장바구니 상품 삭제 
//		public int deleteCart();
//		
//		//장바구니 상품 수량 변경
//		public int modifyAmont();
//
//		//장바구니 상품 옵션 변경
//		public int modifyOption();
//		
//		//장바구니 상품 목록 불러오기
//		public List<CartItem> getCart (String mid);
//		
//		//장바구니 중복 상품 확인
//		public CartItem checkSame();

}
