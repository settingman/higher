package com.hyundai.higher.service.skinMBTI;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
/**
 * @since : 2023. 03. 16.
 * @FileName: SkinMBTIService.java
 * @author : 신수진
 * @설명 : 피부 타입별 정보 불러오기, 결과 저장, 포인트 지급 서비스
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 03. 16.    신수진    	최초 생성   
 * 2023. 04. 10.	신수진		피부 타입별 정보 선택
 * 2023. 04. 12.	신수진		피부 타입 저장, 최초 진단 시 포인트 지급 
 * </pre>
 */
public interface SkinMBTIService {
	
	// 피부 타입별 정보 선택
	public SkinMBTIDTO selectSkinMBTI(String stype);
	
	// 피부타입 저장
	public void updateMBTI(String mid, String mbti, String mbti_scores);
	
	// 포인트 지급
	public void addPoint(String mid);
	
}
