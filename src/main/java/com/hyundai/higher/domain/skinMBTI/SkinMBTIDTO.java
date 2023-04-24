package com.hyundai.higher.domain.skinMBTI;

import lombok.Data;

/**
 * @since : 2023. 03. 16.
 * @FileName: SkinMBTIDTO.java
 * @author : 신수진
 * @설명 : 피부 MBTI 타입 정보
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 16.     신수진       피부 MBTI 타입 정보 DTO
 *     </pre>
 */
@Data
public class SkinMBTIDTO {

	private String stype;
	private String sexplain;
	private String stitle;
	private String spros;
	private String scons;
	private String ssolution;
	private String stag;
	private int templateid;
}
