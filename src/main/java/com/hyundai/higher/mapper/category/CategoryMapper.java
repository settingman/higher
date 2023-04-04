package com.hyundai.higher.mapper.category;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.category.BrandDTO;
import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.category.MBTIDTO;

@Mapper
public interface CategoryMapper {
	
	// 카테고리-중분류 전체 목록
	public List<CategoryDTO> cateListAll(String dept1no);
	
	// mbti 전체 목록
	public List<MBTIDTO> mbtiCateListAll();
	
	// 브랜드 전체 목록
	public List<BrandDTO> brandCateListAll(String dept1no);

}
