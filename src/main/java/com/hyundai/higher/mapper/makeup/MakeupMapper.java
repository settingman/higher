package com.hyundai.higher.mapper.makeup;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;

/**
 * @since   : 2023. 3. 24.
 * @FileName: MakeupMapper.java
 * @author  : 이세아
 * @설명    : 메이크업 상담 결과 제품 추천 기능 Mapper

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 24.     이세아      create
 * 2023. 3. 25.     이세아      상담 결과에 따른 제품 추출
 * </pre>
 */
@Mapper
public interface MakeupMapper {
	
	// Lip 제품 선정
	public List<LipVO> PickLip(String optcolor);
	
	// Blush 제품 선정
	public List<BlushVO> PickBlush(String optcolor);
	
	// Foundation 제품 선정
	public List<FoundationVO> PickFoundation(String optcolor);

}
