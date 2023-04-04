package com.hyundai.higher.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.category.BrandDTO;
import com.hyundai.higher.domain.category.CategoryAllDTO;
import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.category.MBTIDTO;
import com.hyundai.higher.mapper.category.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper mapper;
	
	@Override
	public CategoryAllDTO cateAll() {

		// 카테고리 중분류 - 스킨케어
		List<CategoryDTO> skin = mapper.cateListAll("10");
		
		// 카테고리 중분류 - 메이크업 
		List<CategoryDTO> make = mapper.cateListAll("20");
		
		// 카테고리 mbti
		List<MBTIDTO> mbti = mapper.mbtiCateListAll();
		
		// 카테고리 브랜드 - 스킨케어
		List<BrandDTO> skinBrand = mapper.brandCateListAll("10");
		
		// 카테고리 브랜드 - 메이크업
		List<BrandDTO> makeBrand = mapper.brandCateListAll("20");
		
		CategoryAllDTO cateDTO = new CategoryAllDTO();
		cateDTO.setSkinDTO(skin);
		cateDTO.setMbtiDTO(mbti);
		cateDTO.setSkinBrandDTO(skinBrand);
		cateDTO.setMakeDTO(make);
		cateDTO.setMakeBrandDTO(makeBrand);
		
		return cateDTO;
	}

}
