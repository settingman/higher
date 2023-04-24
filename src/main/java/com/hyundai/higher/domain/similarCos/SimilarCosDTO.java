package com.hyundai.higher.domain.similarCos;

import lombok.Data;

/**
 * @since : 2023. 04. 01.
 * @FileName: SimilarCosDTO.java
 * @author : 신수진
 * @설명 : 유사 성분 추천 상품 정보
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 01.     신수진       유사 성분 추천 상품 정보 DTO
 *     </pre>
 */
@Data
public class SimilarCosDTO {

	private String pcode;
	
	private int total;
	private int percent;
	
	private String pname;
	private String iloc;
	private int bno;
	private String bname;
	private int pprice;
	
	private int rates;
	private int rcnt;
	
}
