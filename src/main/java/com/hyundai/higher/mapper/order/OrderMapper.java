package com.hyundai.higher.mapper.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.cart.OrderItem;
import com.hyundai.higher.domain.order.OrderSheet;

/**
 * @since : 2023. 3. 28.
 * @FileName: OrderMapper.java
 * @author : 박성환
 * @설명 : 주문 데이터 DB 처리
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 28.     박성환      	최초 생성
 *     </pre>
 */
@Mapper
public interface OrderMapper {

	
	// 주문 저장
	public void insertOrder(@Param("orderSheet") OrderSheet orderSheet, @Param("mId") String mId);

	// 주문 아이템 리스트 저장
	public void insertOrderList(@Param("oId") String oId, @Param("pCode") String pCode, @Param("pAmount") int pAmount, @Param("mId") String mId);

	// 장바구니 상품 - >  주문페이지 
	public OrderItem selectCartItem(@Param("mId") String mId, @Param("pCode") String pCode);

	// 주문 내역 조회
	public List<OrderSheet> findOrderList(@Param("mid") String mId, @Param("ordStrtDt") String ordStrtDt,
			@Param("ordEndDt") String ordEndDt, @Param("itemNm") String itemNm);

}
