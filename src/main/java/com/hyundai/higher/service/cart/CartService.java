package com.hyundai.higher.service.cart;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.cart.CartItem; 

/**
 * @since : 2023. 3. 26.
 * @FileName: CartService.java
 * @author : 박서현
 * @설명 : 장바구니 기능을 위한 서비스
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 26.		박서현		최초 생성
 * 2023. 3. 31.		박서현		기능 구현
 * 2023. 4. 10.		박서현		getCount 추가		
 *     </pre>
 */
public interface CartService {
	
		//상품 장바구니 담기
		public int addCart(CartItem cart);
		
		//장바구니 상품 삭제 
		public int deleteCart(String pcode, String optname, String mid);
		
		//장바구니 상품 수량 변경
		public int modifyAmount(CartItem cart, String mid);

		//장바구니 상품 옵션 변경
		public int modifyOption(CartItem cart, String mid);
		
		//장바구니 상품 목록 불러오기
		public List<CartItem> getCart (String mid);
		
		//장바구니 개수 불러오기
		public int getCount(@Param("mid") String mid);

}
