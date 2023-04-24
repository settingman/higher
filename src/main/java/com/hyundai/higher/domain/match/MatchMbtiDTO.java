package com.hyundai.higher.domain.match;

import lombok.Data;
/**
 * @since : 2023. 3. 25.
 * @FileName: MatchMbtiDTO.java
 * @author : 신수진
 * @설명 : MBTI 정보 DTO
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 25.     신수진       매치 페이지 MBTI 정보 DTO
 *     </pre>
 */
@Data
public class MatchMbtiDTO {

	private String stype;
	private String stitle;
	private String spros;
	private String scons;
	private String ssolution;
	private String sexplain;
	private String stag;
	
}
