package com.hyundai.higher.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.order.OrderSheet;

/**
 * @since   : 2023. 3. 28.
 * @FileName: OrderMapper.java
 * @author  : 박성환
 * @설명    : 주문 데이터 DB 처리

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 28.     박성환      	최초 생성
 * </pre>
 */
@Mapper
public interface OrderMapper {
	
	
	public void insertOrder(@Param("orderSheet") OrderSheet orderSheet, @Param("mId") String mId);

}
