package com.hyundai.higher.service.compare;

import org.springframework.beans.factory.annotation.Autowired;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.mapper.compare.CompareMapper;

public class CompareServieImpl implements CompareService{

	@Autowired
	private CompareMapper cMapper;
	
	@Override
	public ProductDTO getInfo(String pcode) {
		return cMapper.getInfo(pcode);
	}

}
