package com.hyundai.higher.mapper.similarCos;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.similarCos.SimilarCosDTO;

public class SimilarCosMapperTest implements SimilarCosMapper {

	@Override
	public List<SimilarCosDTO> recoList(String pcode, int dept1no, int dept2no, List<String> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> searchProdList(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> selectProdList(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
