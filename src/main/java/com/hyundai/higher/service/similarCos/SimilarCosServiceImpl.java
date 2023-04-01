package com.hyundai.higher.service.similarCos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.mapper.similarCos.SimilarCosMapper;
import com.hyundai.higher.service.product.ProductService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SimilarCosServiceImpl implements SimilarCosService {

	@Autowired
	private ProductService pService;
	
	@Autowired
	private SimilarCosMapper mapper;
	
	// 유사성분템 제품 추천
	@Override
	public List<ProductDetailDTO> recogProducts(String pcode) {
		
		ProductDetailDTO pDTO = pService.productDetail(pcode);
		int dept1no = pDTO.getProductDTO().getDept1no();
		int dept2no = pDTO.getProductDTO().getDept2no();
		
		String ingredients = pDTO.getProductDTO().getPingredient();
		
		String[] iList = ingredients.split(", ");
		
		System.out.println(Arrays.toString(iList));
		System.out.println("len : " + iList.length);
		
		List<String> list = new ArrayList<>();
		
		for(String i : iList) {
			list.add(i);
		}
		
		mapper.cntList(pcode, dept1no, dept2no, list);
		
		
		log.info(list);
		
		
		
		return null;
	}

	// 상품명 검색 조회(AJAX)
	@Override
	public List<ProductDTO> prodNameList(String keyword) {

		return mapper.searchProdList(keyword);
	}

	// 상품 조회
	@Override
	public List<ProductDTO> prodList(String keyword) {

		return mapper.selectProdList(keyword);
	}

	// 인기 상품 목록
	@Override
	public List<ProductDTO> bestProdList() {

		return mapper.selectBestProdList();
	}

}
