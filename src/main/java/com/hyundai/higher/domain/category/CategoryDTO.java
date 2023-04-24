package com.hyundai.higher.domain.category;

import lombok.Data;

/**
 * @since : 2023. 3. 16.
 * @FileName: CategoryDTO.java
 * @author : 신수진
 * @설명 : 카테고리 정보
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 16.     신수진       카테고리 정보 DTO
 *     </pre>
 */
@Data
public class CategoryDTO {

	private String dept1no;
	private String dept1name;
	private String dept2name;
	private String dept2no;
}
