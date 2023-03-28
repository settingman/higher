package com.hyundai.higher.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.cart.CartDTO;
import com.hyundai.higher.mapper.cart.CartMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cMapper;

	@Override
	public int addCart(CartDTO cart) {
		cMapper.addCart(cart);
		return 1;

	} 
	


//	@Override
//	public int deleteCart() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int modifyAmont() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int modifyOption() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public List<CartItem> getCart(String mid) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public CartItem checkSame() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
