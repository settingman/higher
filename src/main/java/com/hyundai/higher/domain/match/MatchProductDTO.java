package com.hyundai.higher.domain.match;

import lombok.Data;

/**
 * @since : 2023. 4. 01.
 * @FileName: MatchProductDTO.java
 * @author : 박서현
 * @설명 :  화장품 매칭 상품 DTO
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 04. 01.     박서현       최초 생성
 *     </pre>
 */
@Data
public class MatchProductDTO {
	
	private String pcode;
	private String pname;
	private String dept1no;
	private String dept2no;
	private String pmbti;
	private String iloc;
	private String bname;
	private String pprice;
	private int rates;
	private int rcnt;
	
	private String pvolume;
	private String ptype;
	private String pingredient;
	private String optname;


}
