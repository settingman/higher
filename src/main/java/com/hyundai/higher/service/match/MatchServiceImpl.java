package com.hyundai.higher.service.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.category.MBTIDTO;
import com.hyundai.higher.domain.match.MatchMbtiDTO;
import com.hyundai.higher.domain.match.MatchProductDTO;
import com.hyundai.higher.domain.match.MemberMBTIDTO;
import com.hyundai.higher.mapper.category.CategoryMapper;
import com.hyundai.higher.mapper.match.MatchMapper;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 04. 01.
 * @FileName: MatchServiceImpl.java
 * @author : 박서현
 * @설명 : 화장품 매칭 기능 구현
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 01.     박서현      최초 생성
 * 2023. 04. 12. 	박서현		
 *     </pre>
 */
@Service
@Log4j2
public class MatchServiceImpl implements MatchService{
	
	@Autowired
	private MatchMapper mMapper;

	@Autowired
	private CategoryMapper cMapper;
	
	@Override
	public List<MatchProductDTO> mbtiProduct(String pmbti, String dept2no) {
		List<MatchProductDTO> product = mMapper.mbtiProduct(pmbti,dept2no); 
		return product;
	}

	@Override
	public MatchMbtiDTO getMbtiInfo(String pmbti) {		
		return mMapper.getMbtiInfo(pmbti);
	}

	@Override
	public List<MatchProductDTO> searchProduct(String keyword) {
		List<MatchProductDTO> product = mMapper.searchProduct(keyword); 
		return product;
	}

	// 회원 mbti 정보 불러오기
	@Override
	public MemberMBTIDTO getMemMBTI(String mid) {
		
		return mMapper.getMemMbti(mid);
	}

	// mbti 카테고리
	@Override
	public List<MBTIDTO> mbtiList() {

		return cMapper.mbtiCateListAll();
	}
	
	@Override
	public String userMbti(String mid) {
		return mMapper.userMbti(mid);
	}

	@Override
	public String[] getIngredient(String pcode) {
		return mMapper.getIngredient(pcode);
	}

	@Override
	public String getEffect(String ingredient, String mbti) {
		return mMapper.getEffect(ingredient, mbti);
	}

	@Override
	public String getOption(String pcode) {
		return mMapper.getOption(pcode);
	}
	
	

}
