package com.hyundai.higher.mapper.cart;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.hyundai.higher.domain.cart.CartItem;

public class CartMapperTest implements CartMapper {

	@Override
	public int addCart(CartItem cart) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCart(String pcode, String optname, String mid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyAmount(CartItem cart, String mid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyOption(CartItem cart, String mid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CartItem> getCart(String mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SameUpdate(CartItem cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int checkSame(CartItem cart) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCount(String mid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
