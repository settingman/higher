package com.hyundai.higher.service.match;

import java.util.List;
import java.util.Map;

import com.hyundai.higher.domain.match.MatchMbtiDTO;
import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.domain.match.MemberMBTIDTO;

/**
 * @since   : 2023. 04. 01.
 * @FileName: MatchService.java
 * @author  : 박서현, 신수진
 * @설명    : 화장품 매칭을 위한 서비스

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 01.    박서현      	최초 생성
 * 2023. 04. 06.	신수진		메인 수정
 * 2023. 04. 12.	신수진		카테고리 수정		
 * </pre>
 */
public interface MatchService {

	//해당하는 mbti의 상품 불러오기
	public List<MatchProductDTO> mbtiProduct(String dept2no, String pmbti);
	
	//해당하는 mbti의 정보 불러오기
	public MatchMbtiDTO getMbtiInfo(String pmbti);

	//상품 검색 조회
	public List<MatchProductDTO> searchProduct(String keyword);

	// 회원 mbti 정보 불러오기
	public MemberMBTIDTO getMemMBTI(String mid);
	
	// mbti 카테고리
	public Map<String, String> cateMap();

	//회원의 mbti 조회
	public String userMbti(String mid);
	
	//pcode에 해당하는 상품의 성분 불러오기
	public String[] getIngredient(String pcode);
	
	//해당 성분의 피부 타입 매칭 결과 불러오기
	public String getEffect(String ingredient, String mbti);
	
	//해당 상품의 옵션 불러오기
	public String getOption(String pcode);

}
