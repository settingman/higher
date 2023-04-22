package com.hyundai.higher.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.category.BrandDTO;
import com.hyundai.higher.domain.category.CategoryAllDTO;
import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.category.MBTIDTO;
import com.hyundai.higher.mapper.category.CategoryMapper;
/**
 * @since : 2023. 03. 16.
 * @FileName: CategoryServiceImpl.java
 * @author : 신수진
 * @설명 : 헤더 카테고리 목록 서비스
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 16.	신수진		전체 카테고리
 * </pre>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper mapper;
	
	@Override
	public CategoryAllDTO cateAll() {

		// 카테고리 mbti
		List<MBTIDTO> mbti = mapper.mbtiCateListAll();
		
		// 카테고리 중분류 - 스킨케어
		List<CategoryDTO> skin = mapper.cateListAll("10");
		
		// 카테고리 중분류 - 메이크업 
		List<CategoryDTO> make = mapper.cateListAll("20");
		
		// 카테고리 중분류 - 바디/헤어
		List<CategoryDTO> body = mapper.cateListAll("30");
		
		// 카테고리 브랜드 - 스킨케어
		List<BrandDTO> skinBrand = mapper.brandCateListAll("10");
		
		// 카테고리 브랜드 - 메이크업
		List<BrandDTO> makeBrand = mapper.brandCateListAll("20");
		
		// 카테고리 브랜드 - 바디/헤어
		List<BrandDTO> bodyBrand = mapper.brandCateListAll("30");
		
		CategoryAllDTO cateDTO = new CategoryAllDTO();
		cateDTO.setSkinDTO(skin);
		cateDTO.setSkinBrandDTO(skinBrand);
		cateDTO.setMakeDTO(make);
		cateDTO.setMbtiDTO(mbti);
		cateDTO.setMakeBrandDTO(makeBrand);
		cateDTO.setBodyDTO(body);
		cateDTO.setBodyBrandDTO(bodyBrand);
		
		return cateDTO;
	}

}
