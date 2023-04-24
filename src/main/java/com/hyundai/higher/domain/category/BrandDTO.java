package com.hyundai.higher.domain.category;

import lombok.Data;
/**
 * @since : 2023. 3. 16.
 * @FileName: BrandDTO.java
 * @author : 신수진
 * @설명 : 브랜드 정보
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 16.     신수진       브랜드 정보 DTO
 *     </pre>
 */
@Data
public class BrandDTO {
	
	private String bno;
	private String bname;
	private String dept1no;
}
