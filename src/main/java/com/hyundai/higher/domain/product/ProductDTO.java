package com.hyundai.higher.domain.product;

import java.util.List;

import lombok.Data;

/**
 * @since : 2023. 3. 16.
 * @FileName: ProductDTO.java
 * @author : 신수진
 * @설명 : @
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 16.     신수진       상품 정보 DTO
 *     </pre>
 */
@Data
public class ProductDTO {

	private String pcode;
	private String pname;
	private int pprice;
	private int bno;
	private String bname;
	private int dept1no;
	private int dept2no;
	
	private List<ImgDTO> thumbImgList;
	
	private String pvolume;
	private String ptype;
	private String pexp;
	private String puse;
	private String pproduce;
	private String pcountry;
	private String pcaution;
	private String pingredient;
	
	private String pmbti;
	
}
