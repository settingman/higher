package com.hyundai.higher.domain.review;

import lombok.Data;

/**
 * @since : 2023. 04. 14.
 * @FileName: ReviewDTO.java
 * @author : 신수진
 * @설명 : 리뷰 작성 가능한 상품 목록 정보
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 14.     신수진       리뷰 작성 가능한 상품 목록 정보 DTO
 *     </pre>
 */
@Data
public class ReviewProdDTO {

	private String oid;
	private String pcode;
	private String pname;
	private String dept1no;
	private String dept2no;
	private String iloc;
	private String bname;
	private String odate;
	private int pprice;
	
}
