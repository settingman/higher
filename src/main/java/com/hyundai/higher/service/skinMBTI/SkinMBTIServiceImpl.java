package com.hyundai.higher.service.skinMBTI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
import com.hyundai.higher.mapper.skinMBTI.SkinMBTIMapper;

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
