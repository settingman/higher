package com.hyundai.higher.mapper.similarCos;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.similarCos.SimilarCosDTO;

@Mapper
public interface SimilarCosMapper {

	public List<SimilarCosDTO> cntList(String pcode, int dept1no, int dept2no, List<String> list);
	
	
}
