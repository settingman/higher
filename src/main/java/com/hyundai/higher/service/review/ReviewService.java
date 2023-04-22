package com.hyundai.higher.service.review;

import java.util.List;

import com.hyundai.higher.domain.review.ReviewDTO;
import com.hyundai.higher.domain.review.ReviewProdDTO;
/**
 * @since : 2023. 4. 14.
 * @FileName: ReviewService.java
 * @author : 신수진
 * @설명 : 
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 14.    신수진		최초 생성, 리뷰 상품 조회, 리뷰 등록 서비스
 * 2023. 04. 15.	신수진		전체, 상품별 리뷰 조회 서비스	
 *     </pre>
 */
public interface ReviewService {

	// 회원의 리뷰 작성 가능한 상품 목록
	public List<ReviewProdDTO> reviewProdList(String mid);
	
	// 리뷰 등록
	public void createReview(ReviewDTO rDTO);
	
	// 상품별 리뷰 조회
	public List<ReviewDTO> reviewList(String pcode);

	// 전체 리뷰 조회
	public List<ReviewDTO> reviewListAll();
}
