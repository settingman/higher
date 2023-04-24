package com.hyundai.higher.service.match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.category.CategoryDTO;
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
 * @설명 : 화장품 매칭을 위한 서비스Impl
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 01.    박서현      최초 생성
 * 2023. 04. 12.	신수진		카테고리 수정
 *     </pre>
 */
@Service
@Log4j2
public class MatchServiceImpl implements MatchService{
	
	@Autowired
	private MatchMapper mMapper;

	@Autowired
	private CategoryMapper cMapper;
	
	//해당하는 mbti의 상품 불러오기
	@Override
	public List<MatchProductDTO> mbtiProduct(String dept2no, String pmbti) {
		String price = "";
		List<MatchProductDTO> product = mMapper.mbtiProduct(dept2no, pmbti, price); 

		return product;
	}

	//해당하는 mbti의 정보 불러오기
	@Override
	public MatchMbtiDTO getMbtiInfo(String pmbti) {		
		return mMapper.getMbtiInfo(pmbti);
	}

	//상품 검색 조회
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
	public Map<String, String> cateMap() {

		List<CategoryDTO> list = cMapper.cateListAll("10");
		Map<String, String> map = new HashMap<>();
		
		for(int i=0; i<list.size(); i++) {
			map.put(list.get(i).getDept2no(), list.get(i).getDept2name());
		}
		
		return map;
	}
	
	//회원의 mbti 조회
	@Override
	public String userMbti(String mid) {
		return mMapper.userMbti(mid);
	}

	//pcode에 해당하는 상품의 성분 불러오기
	@Override
	public String[] getIngredient(String pcode) {
		return mMapper.getIngredient(pcode);
	}

	//해당 성분의 피부 타입 매칭 결과 불러오기
	@Override
	public String getEffect(String ingredient, String mbti) {
		return mMapper.getEffect(ingredient, mbti);
	}

	//해당 상품의 옵션 불러오기
	@Override
	public String getOption(String pcode) {
		return mMapper.getOption(pcode);
	}

	//해당 상품의 정보 불러오기
	@Override
	public MatchProductDTO getInfo(String pcode) {
		return mMapper.getInfo(pcode);
	}

	//성분의 효과 불러오기
	@Override
	public String getIinfo(String ingredient, String mbti) {
		return mMapper.getIinfo(ingredient, mbti);
	}
	
	

}
