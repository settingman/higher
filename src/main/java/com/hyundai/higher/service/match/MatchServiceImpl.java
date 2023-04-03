package com.hyundai.higher.service.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.match.MatchMbtiDTO;
import com.hyundai.higher.domain.match.MatchProductDTO;
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
 *     </pre>
 */
@Service
@Log4j2
public class MatchServiceImpl implements MatchService{
	
	@Autowired
	private MatchMapper mMapper;

	@Override
	public List<MatchProductDTO> mbtiProduct(String pmbti) {
		List<MatchProductDTO> product = mMapper.mbtiProduct(pmbti); 
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
	
	

}
