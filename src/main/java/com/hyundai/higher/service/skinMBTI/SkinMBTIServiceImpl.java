package com.hyundai.higher.service.skinMBTI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
import com.hyundai.higher.mapper.skinMBTI.SkinMBTIMapper;
/**
 * @since : 2023. 03. 16.
 * @FileName: SkinMBTIServiceImpl.java
 * @author : 신수진
 * @설명 : 피부 타입별 정보 불러오기, 결과 저장, 포인트 지급 서비스를 위한 매퍼 호출
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 16.    신수진    	최초 생성   
 * 2023. 04. 10.	신수진		피부 타입별 정보 선택
 * 2023. 04. 12.	신수진		피부 타입 저장, 최초 진단 시 포인트 지급 
 * </pre>
 */
@Service
public class SkinMBTIServiceImpl implements SkinMBTIService {

	@Autowired
	private SkinMBTIMapper mapper;
	
	// 피부 타입별 정보 선택
	@Override
	public SkinMBTIDTO selectSkinMBTI(String stype) {

		return mapper.selectMBTI(stype);
	}

	// 피부타입 저장
	@Override
	public void updateMBTI(String mid, String mbti, String mbti_scores) {
		
		mapper.updateMBTI(mid, mbti, mbti_scores);
	}

	// 포인트 지급
	@Override
	public void addPoint(String mid) {
		
		mapper.updatePoint(mid);
	}
	
	

}
