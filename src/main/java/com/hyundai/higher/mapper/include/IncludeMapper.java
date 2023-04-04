package com.hyundai.higher.mapper.include;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.category.CategoryDTO;

@Mapper
public interface IncludeMapper {
	
	// 카테고리 목록
	public List<CategoryDTO> categoryListAll();
		
	// 상품 목록 - 카테고리
	public List<CategoryDTO> categoryListSub(String dept1no);

}
