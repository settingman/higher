package com.hyundai.higher.domain.review;

import lombok.Data;

/**
 * @since : 2023. 04. 14.
 * @FileName: ReviewDTO.java
 * @author : 신수진
 * @설명 : 리뷰 정보
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 14.     신수진       리뷰 정보 DTO
 *     </pre>
 */
@Data
public class ReviewDTO {

	private String mid;
	private String mbti;
	
	private int rno;
	private int rrate;
	private String rdate;
	private String rcontent;
	private String pcode;
	private String oid;
	private String rtitle;
	
	private String pname;
	private String bname;

}
