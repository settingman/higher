package com.hyundai.higher.service.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.review.ReviewProdDTO;
import com.hyundai.higher.mapper.review.ReviewMapper;
/**
 * @since : 2023. 4. 14.
 * @FileName: ReviewServiceImpl.java
 * @author : 신수진
 * @설명 : 
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 14.    신수진		최초 생성    
 *     </pre>
 */
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper mapper;
	
	// 회원의 리뷰 작성 가능한 상품 목록
	@Override
	public List<ReviewProdDTO> reviewProdList(String mid) {
		
		return mapper.reviewProdList(mid);
	}

}
