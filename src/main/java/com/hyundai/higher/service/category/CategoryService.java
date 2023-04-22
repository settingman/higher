package com.hyundai.higher.service.category;

import com.hyundai.higher.domain.category.CategoryAllDTO;
/**
 * @since : 2023. 03. 16.
 * @FileName: CategoryService.java
 * @author : 신수진
 * @설명 : 헤더 카테고리 목록 서비스
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 16.	신수진		전체 카테고리
 * </pre>
 */
public interface CategoryService {
	
	// 카테고리 목록 전체
	public CategoryAllDTO cateAll();
	
}
