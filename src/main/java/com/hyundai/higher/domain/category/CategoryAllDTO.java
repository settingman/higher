package com.hyundai.higher.domain.category;

import java.util.List;

import lombok.Data;
/**
 * @since : 2023. 3. 16.
 * @FileName: CategoryAllDTO.java
 * @author : 신수진
 * @설명 : 대분류/중분류 전체 카테고리 정보
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 16.     신수진       전체 카테고리 정보 DTO
 *     </pre>
 */
@Data
public class CategoryAllDTO {

	private List<CategoryDTO> skinDTO;
	private List<MBTIDTO> mbtiDTO;
	private List<BrandDTO> skinBrandDTO;
	
	private List<CategoryDTO> makeDTO;
	private List<BrandDTO> makeBrandDTO;
	
	private List<CategoryDTO> bodyDTO;
	private List<BrandDTO> bodyBrandDTO;
}
