package com.hyundai.higher.service.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.cart.CartItem;
import com.hyundai.higher.mapper.cart.CartMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cMapper;

	@Override
	public int addCart(CartItem cart) {
		
		int checkSame = cMapper.checkSame(cart);
		
		if(checkSame == 1 ) {//중복상품이 있는 경우
			cMapper.SameUpdate(cart);
			return 2;
		}
		
		try {
			return cMapper.addCart(cart);
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	} 

	@Override
	public int deleteCart(String pcode, String optname, String mid) {
		return cMapper.deleteCart(pcode, optname, mid);
	}

	@Override
	public int modifyAmount(CartItem cart, String mid) {
		
		return cMapper.modifyAmount(cart, mid);
	}

	@Override
	public int modifyOption(CartItem cart, String mid) {
		 
		return cMapper.modifyOption(cart, mid);
	}

	@Override
	public List<CartItem> getCart(String mid) {

		List<CartItem> cart = cMapper.getCart(mid); 
		return cart;
	}


}
