package com.hyundai.higher.mapper.compare;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.product.ProductDTO;

/**
 * @since : 2023. 4. 3.
 * @FileName: CompareMapper.java
 * @author : 박서현
 * @설명 : 상품 비교하기 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 3.		박서현		DateBase Compare Mapper Interface
 *     </pre>
 */
@Mapper
public interface CompareMapper {
	
	//비교 상품의 성분 정보 불러오기 
	public ProductDTO getInfo(@Param("pcode") String pcode);

}
