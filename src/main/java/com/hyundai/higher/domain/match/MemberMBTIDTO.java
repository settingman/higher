package com.hyundai.higher.domain.match;

import lombok.Data;
/**
 * @since : 2023. 3. 25.
 * @FileName: MemberMbtiDTO.java
 * @author : 신수진
 * @설명 : 회원 MBTI 정보 DTO
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 25.     신수진       회원 MBTI 정보 DTO
 *     </pre>
 */
@Data
public class MemberMBTIDTO {

	private String mid;
	private String mbti;
	private String stag;
	private String mbti_scores;
}
