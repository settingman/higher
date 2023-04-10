package com.hyundai.higher.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.product.ImgDTO;
import com.hyundai.higher.domain.product.OptionDTO;
import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.mapper.category.CategoryMapper;
import com.hyundai.higher.mapper.product.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper mapper;
	
	@Autowired
	private CategoryMapper cMapper;

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

	// 상품 분류
	@Override
	public List<CategoryDTO> cateList(String dept1no) {
		
		return cMapper.cateListAll(dept1no);
	}

	// 중분류 이름
	@Override
	public CategoryDTO getDept2name(String dept1no, String dept2no) {

		return mapper.getDept2name(dept1no, dept2no);
	}

}
