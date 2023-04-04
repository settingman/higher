package com.hyundai.higher.service.include;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.category.CategoryDTO;
import com.hyundai.higher.mapper.include.IncludeMapper;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 3. 15.
 * @FileName: IncludeServiceImpl.java
 * @author : 신수진
 * @설명 : 인클루드(헤더/푸터) 관련 기능 구현
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     신수진      		
 *     </pre>
 */

@Log4j2
@Service
public class IncludeServiceImpl implements IncludeService {

	@Autowired
	private IncludeMapper mapper;
	
	// 전체 화장품 카테고리 목록
	@Override
	public List<CategoryDTO> categoryListAll() {

		return mapper.categoryListAll();
	}

}
