package com.hyundai.higher.mapper.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.cart.CartItem;

/**
 * @since : 2023. 3. 26.
 * @FileName: CartMapper.java
 * @author : 박서현
 * @설명 : 장바구니 mapper Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 26.		박서현		최초 생성
 * 2023. 3. 31.		박서현		기능 구현
 * 2023. 4. 10.		박서현		getCount 추가		
 *     </pre>
 */
@Mapper
public interface CartMapper {

	//상품 장바구니 담기
	public int addCart(@Param("item") CartItem cart) throws Exception;
	
	//장바구니 상품 삭제 
	public int deleteCart(@Param("pcode") String pcode, @Param("optname") String optname, @Param("mid") String mid);
	
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
		
	//장바구니 개수 불러오기
	public int getCount(@Param("mid") String mid);
}
