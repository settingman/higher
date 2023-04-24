package com.hyundai.higher.domain.product;

import lombok.Data;
/**
 * @since : 2023. 3. 20.
 * @FileName: ImgDTO.java
 * @author : 신수진
 * @설명 : 상품 이미지 정보 
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 20.     신수진       상품 이미지 정보 DTO
 *     </pre>
 */
@Data
public class ImgDTO {

	private String pcode;
	private String itype;
	private String iloc;
	
}
