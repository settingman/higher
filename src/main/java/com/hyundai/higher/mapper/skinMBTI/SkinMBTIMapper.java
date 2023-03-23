package com.hyundai.higher.mapper.skinMBTI;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;

@Mapper
public interface SkinMBTIMapper {
	
	// 타입별 설명 선택
	public SkinMBTIDTO selectMBTI(String stype);
}
