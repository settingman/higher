package com.hyundai.higher.service.skinMBTI;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;

public interface SkinMBTIService {
	
	// 피부 타입별 정보 선택
	public SkinMBTIDTO selectSkinMBTI(String stype);
	
	// 피부타입 저장
	public void updateMBTI(String mid, String mbti);
	
}
