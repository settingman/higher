package com.hyundai.higher.domain.product;

import java.util.List;

import lombok.Data;
/**
 * @since : 2023. 3. 20.
 * @FileName: ProductDetailDTO.java
 * @author : 신수진
 * @설명 : 상품 상세, 썸네일 이미지, 세부 이미지, 옵션 정보 
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 20.     신수진       상품 상세, 썸네일 이미지, 세부 이미지, 옵션 정보 DTO
 *     </pre>
 */
@Data
public class ProductDetailDTO {
	
	private ProductDTO productDTO;
	private List<ImgDTO> thumbImgList;
	private List<ImgDTO> detailImgList;
	private List<OptionDTO> optList;
}
