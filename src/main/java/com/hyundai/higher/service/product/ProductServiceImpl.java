package com.hyundai.higher.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.product.CategoryDTO;
import com.hyundai.higher.domain.product.ImgDTO;
import com.hyundai.higher.domain.product.OptionDTO;
import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.mapper.product.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper mapper;
	
	// 상품 카테고리 리스트(하위)
	@Override
	public List<CategoryDTO> categoryListSub(String dept1no) {
		
		return mapper.categoryListSub(dept1no);
	}

	// 상품 목록
	@Override
	public List<ProductDTO> productList(String dept1no, String dept2no) {
		
		return mapper.productList(dept1no, dept2no);
	}

	// 상품 세부 정보 전체
	@Override
	public ProductDetailDTO productDetail(String pcode) {
		
		ProductDTO prodDTO = mapper.productDetailInfo(pcode);
		List<ImgDTO> thumbImgList = mapper.imgThumbList(pcode);
		List<ImgDTO> detailImgList = mapper.imgDetailList(pcode);
		List<OptionDTO> optList = mapper.optList(pcode);
		
		ProductDetailDTO prodDetailDTO = new ProductDetailDTO();
		prodDetailDTO.setProductDTO(prodDTO);
		prodDetailDTO.setThumbImgList(thumbImgList);
		prodDetailDTO.setDetailImgList(detailImgList);
		prodDetailDTO.setOptList(optList);
		
		return prodDetailDTO;
	}

}
