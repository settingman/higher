package com.hyundai.higher.domain.makeup;

import lombok.Data;

/**
 * @since   : 2023. 4. 7.
 * @FileName: ResultVO.java
 * @author  : 이세아
 * @설명    : 결과 전송을 위한 VO

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 7.     이세아       create
 * </pre>
 */

@Data
public class ResultVO {
	
	private String rid;
	private String result_img;
	private String lip;
	private String lip_pcode;
	private String lip_opt;
	private String blush;
	private String blush_pcode;
	private String blush_opt;
	private String face;
	private String face_pcode;
	private String face_opt;
	private String artistcmt;

}