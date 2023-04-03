package com.hyundai.higher.mapper.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.cart.CartItem;

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
	public int addCart(@Param("item") CartItem cart) throws Exception;
	
	//장바구니 상품 삭제 
	public int deleteCart(@Param("pcode") String pcode, @Param("optname") String optname, @Param("mid") String mid);
	
	//장바구니 상품 선택 삭제 
	//public int deleteCarts(@Param("pcode") String pcode, @Param("optname") String optname, @Param("mid") String mid);
	

	//장바구니 상품 수량 변경
	public int modifyAmount(@Param("item") CartItem cart, @Param("mid") String mid);

	//장바구니 상품 옵션 변경
	public int modifyOption(@Param("item") CartItem cart, @Param("mid") String mid);
	
	//장바구니 상품 목록 불러오기
	public List<CartItem> getCart(@Param("mid") String mid);
	
	//장바구니 중복 상품 수량 업데이트
	public void SameUpdate(@Param("item") CartItem cart);
	
	//장바구니 중복 검사
	public int checkSame(@Param("item") CartItem cart);
	
	
	//장바구니 상품의 옵션들 불러오기 
}
