package com.hyundai.higher.domain.makeup;

import lombok.Data;

/**
 * @since   : 2023. 4. 3.
 * @FileName: MbtiVO.java
 * @author  : 이세아
 * @설명    : 예약내역과 마이페이지를 위한 고객의 mbti 차트를 위한 상세결과

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 3.     이세아       create
 * </pre>
 */

@Data
public class MbtiVO {
	
	private String mid;
	private String mbti;
	private String mbti_scores;

}
