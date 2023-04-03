package com.hyundai.higher.service.match;

import java.util.List;

import com.hyundai.higher.domain.match.MatchMbtiDTO;
import com.hyundai.higher.domain.match.MatchProductDTO;

/**
 * @since   : 2023. 04. 01.
 * @FileName: MatchService.java
 * @author  : 박서현
 * @설명    : 화장품 매칭을 위한 서비스

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 01.     박서현      	최초 생성
 * </pre>
 */
public interface MatchService {

	//해당하는 mbti의 상품 불러오기
	public List<MatchProductDTO> mbtiProduct(String pmbti);
	
	//해당하는 mbti의 정보 불러오기
	public MatchMbtiDTO getMbtiInfo(String pmbti);

	//상품 검색 조회
	public List<MatchProductDTO> searchProduct(String keyword);
}
