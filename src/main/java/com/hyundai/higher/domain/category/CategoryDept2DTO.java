package com.hyundai.higher.domain.category;

import lombok.Data;

/**
 * @since : 2023. 3. 16.
 * @FileName: CategoryDept2DTO.java
 * @author : 신수진
 * @설명 : @
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 16.     신수진       중분류 카테고리 정보 DTO
 *     </pre>
 */
@Data
public class CategoryDept2DTO {
	
	private String dept1no;
	private String dept2no;
	private String dept2name;
	private int pcnt;
}
