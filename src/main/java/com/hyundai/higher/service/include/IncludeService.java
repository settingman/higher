package com.hyundai.higher.service.include;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.category.CategoryDTO;

/**
 * @since : 2023. 3. 15.
 * @FileName: IncludeService.java
 * @author : 신수진
 * @설명 : 인클루드(헤더/푸터) 관련 기능 구현
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     신수진      		
 *     </pre>
 */

public interface IncludeService {

	// 전체 화장품 카테고리 목록
	public List<CategoryDTO> categoryListAll();
}
