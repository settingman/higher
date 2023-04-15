package com.hyundai.higher.mapper.review;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.review.ReviewDTO;
import com.hyundai.higher.domain.review.ReviewProdDTO;

/**
 * @since : 2023. 4. 14.
 * @FileName: ReviewMapper.java
 * @author : 신수진
 * @설명 : 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 14.	신수진		DateBase Review Mapper Interface
 * 2023. 04. 15.	신수진		리뷰 조회
 *     </pre>
 */
@Mapper
public interface ReviewMapper {

	// 회원이 작성 가능한 리뷰 상품 목록 select
	public List<ReviewProdDTO> reviewProdList(String mid);
	
	// 리뷰 등록
	public void createReview(ReviewDTO rDTO);
	
	// 상품별 리뷰 조회
	public List<ReviewDTO> reviewList(String pcode);
	
	// 전체 리뷰 조회
	public List<ReviewDTO> reviewListAll();
	
}
