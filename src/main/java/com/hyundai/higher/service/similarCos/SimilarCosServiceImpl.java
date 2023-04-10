package com.hyundai.higher.service.similarCos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.domain.similarCos.SimilarCosDTO;
import com.hyundai.higher.mapper.category.CategoryMapper;
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
	
	@Autowired
	private CategoryMapper cMapper;
	

	// 유사성분템 제품 추천
	@Override
	public List<SimilarCosDTO> recogProducts(String pcode) {
		
		// 선택한 상품의 정보
		ProductDetailDTO pDTO = pService.productDetail(pcode);
		int dept1no = pDTO.getProductDTO().getDept1no();
		int dept2no = pDTO.getProductDTO().getDept2no();
		
		// 선택한 상품에 포함된 성분
		String ingredients = pDTO.getProductDTO().getPingredient();
		
		// 선택한 상품에 포함된 성분 리스트에 담기
		String[] iList = ingredients.replaceAll(",", " ").replaceAll("\\s+", " ").split(" ");
		List<String> list = new ArrayList<>();
		for(String i : iList) {
			list.add(i);
		}
		
		// 선택한 상품의 전체 성분 수
		int len = iList.length;
		
		// 유사 성분 제품 리스트
		List<SimilarCosDTO> similarCostList =  mapper.cntList(pcode, dept1no, dept2no, list);
		
		log.info("list : " + similarCostList);
		
		// 성분 유사도 계산
		for(SimilarCosDTO s : similarCostList) {
			double cnt = s.getTotal();
			int similarPercent = (int)(cnt / len * 100);
			s.setPercent(similarPercent);
		}
		
		
		return similarCostList;
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

}
