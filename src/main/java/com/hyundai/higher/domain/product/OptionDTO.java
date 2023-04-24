package com.hyundai.higher.domain.product;

import lombok.Data;
/**
 * @since : 2023. 3. 20.
 * @FileName: OptionDTO.java
 * @author : 신수진
 * @설명 : 상품 옵션 정보 
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 20.     신수진       상품 옵션 정보 DTO
 *     </pre>
 */
@Data
public class OptionDTO {

	private String pcode;
	private String optname;
	private String optcolor;
	
}
