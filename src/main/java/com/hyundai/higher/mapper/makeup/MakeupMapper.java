package com.hyundai.higher.mapper.makeup;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.domain.makeup.MbtiVO;
import com.hyundai.higher.domain.makeup.ResultVO;
import com.hyundai.higher.domain.member.Member;

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
 * 2023. 3. 28.		이세아	  아티스트 픽 이후 결과 DB 저장 매퍼
 * 2023. 4.  2.		이세아	  유사 상품
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
	
	// 아티스트가 선정한 제품 예약 결과 내역에 저장
	public void insertResult(ResultVO result);
	
	// 고객 정보
	public Member MemInfo(String rid);
	
	// 마일리지 추가
	public void mileageupdate(String mid);
	
	// mbti 정보 찾기
	public MbtiVO findmbti(String mid);

}
