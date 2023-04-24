package com.hyundai.higher.mapper.skinMBTI;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
/**
 * @since : 2023. 03. 31.
 * @FileName: SkinMBTIMapper.java
 * @author : 신수진
 * @설명 : 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 31.    신수진      	DateBase skinMBTI Mapper Interface
 * 2023. 04. 12.	신수진		포인트 지급
 *     </pre>
 */
@Mapper
public interface SkinMBTIMapper {
	
	// 타입별 설명 선택
	public SkinMBTIDTO selectMBTI(String stype);
	
	// 피부타입 저장
	public void updateMBTI(String mid, String mbti, String mbti_scores);
	
	// 포인트 지급
	public void updatePoint(String mid);
	
}
