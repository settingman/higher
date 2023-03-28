package com.hyundai.higher.mapper.cart;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.cart.CartDTO;

/**
 * @since : 2023. 3. 26.
 * @FileName: CartMapper.java
 * @author : 박서현s
 * @설명 : 장바구니 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 26.		박서현		DateBase Cart Mapper Interface
 *     </pre>
 */
@Mapper
public interface CartMapper {

	//상품 장바구니 담기
	public int addCart(@Param("item") CartDTO cart);
	
//	//장바구니 상품 삭제 
//	public int deleteCart();
//	
//	//장바구니 상품 수량 변경
//	public int modifyAmount();
//
//	//장바구니 상품 옵션 변경
//	public int modifyOption();
//	
//	//장바구니 상품 목록 불러오기
//	public List<CartItem> getCart (String mid);
//	
//	//장바구니 중복 상품 확인
//	public CartItem checkSame(CartItem cart);
}
