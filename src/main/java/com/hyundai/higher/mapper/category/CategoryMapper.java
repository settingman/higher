package com.hyundai.higher.mapper.category;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.category.BrandDTO;
import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.domain.category.MBTIDTO;
/**
 * @since : 2023. 3. 16.
 * @FileName: CategoryMapper.java
 * @author : 신수진
 * @설명 : 카테고리 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 16.    신수진      	최초 생성, 전체 카테고리 조회
 *     </pre>
 */
@Mapper
public interface CategoryMapper {
	
	// 카테고리-중분류 전체 목록
	public List<CategoryDTO> cateListAll(String dept1no);
	
	// mbti 전체 목록
	public List<MBTIDTO> mbtiCateListAll();
	
	// 브랜드 전체 목록
	public List<BrandDTO> brandCateListAll(String dept1no);

}
