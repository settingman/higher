package com.hyundai.higher.mapper.match;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.match.MatchMbtiDTO;
import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.domain.match.MemberMBTIDTO;

/**
 * @since : 2023. 04. 01.
 * @FileName: MatchMapper.java
 * @author : 박서현
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 01.    박서현       DateBase Match Mapper Interface
 * 2023. 04. 06.	신수진
 * 2023. 04. 12. 	박서현		
 * </pre>
 */

@Mapper
public interface MatchMapper {

		//해당하는 mbti의 상품 불러오기
		public List<MatchProductDTO> mbtiProduct(String pmbti, String dept2no);
		
		//해당하는 mbti의 정보 불러오기
		public MatchMbtiDTO getMbtiInfo(String pmbti);

		//상품 검색 조회 
		public List<MatchProductDTO> searchProduct(String keyword);
		
		// 회원 mbti 정보 불러오기
		public MemberMBTIDTO getMemMbti(String mid);

		//회원의 mbti 조회
		public String userMbti(String mid);
		
		//pcode에 해당하는 상품의 성분 불러오기
		public String[] getIngredient(String pcode);
		
		//해당 성분의 피부 타입 매칭 결과 불러오기
		public String getEffect(String ingredient, String mbti);
		
		//해당 상품의 옵션 불러오기
		public String getOption(String pcode);
		
		//해당 상품의 옵션 불러오기
		public MatchProductDTO getInfo(String pcode);
}
