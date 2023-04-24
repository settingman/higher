package com.hyundai.higher.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.category.MBTIDTO;
import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.domain.product.ImgDTO;
import com.hyundai.higher.domain.product.OptionDTO;
import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.product.ProductDetailDTO;
import com.hyundai.higher.mapper.category.CategoryMapper;
import com.hyundai.higher.mapper.match.MatchMapper;
import com.hyundai.higher.mapper.product.ProductMapper;

/**
 * @since : 2023. 3. 16.
 * @FileName: ProductService.java
 * @author : 신수진
 * @설명 : 
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 16.    신수진    	최초 생성 
 * 2023. 04. 12.	신수진		피부타입 카테고리 추가  		
 *     </pre>
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper mapper;
	
	@Autowired
	private CategoryMapper cMapper;
	
	@Autowired
	private MatchMapper mMapper;

	// 상품 목록
	@Override
	public List<ProductDTO> productList(String dept1no, String dept2no, String price) {
		
		return mapper.productList(dept1no, dept2no, price);
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

	// mbti 목록
	@Override
	public List<MBTIDTO> mbtiList() {
		return cMapper.mbtiCateListAll();
	}

	@Override
	public List<MatchProductDTO> mbtiProdList(String mbti, String price) {
		
		String dept2no = "";
		
		return mMapper.mbtiProduct(dept2no, mbti, price);
	}

}
