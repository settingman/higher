package com.hyundai.higher.mapper.order;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.hyundai.higher.domain.cart.OrderItem;
import com.hyundai.higher.domain.order.OrderSheet;

public class OrderMapperTest implements OrderMapper {

	@Override
	public void insertOrder(OrderSheet orderSheet, String mId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertOrderList(String oId, String pCode, int pAmount, String mId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrderItem selectCartItem(String mId, String pCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderSheet> findOrderList(String mId, String ordStrtDt, String ordEndDt, String itemNm) {
		// TODO Auto-generated method stub
		return null;
	}

}
