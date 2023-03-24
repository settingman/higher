package com.hyundai.higher.domain.cart;

import java.sql.Date;

import com.hyundai.higher.domain.member.MemberRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @since : 2023. 3. 23.
 * @FileName: CartItem.java
 * @author : 박성환
 * @설명 : 장바구니 객체
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 23.     박성환      	최초 생성
 *     </pre>
 */
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

	private String pCode;
	private String pName;
	private Integer pPRICE;
	private String Brand;
	private String Color;
	private Integer pAmount;
	private String pOption;
	private String IMAGE_PATH;

	public CartItem(String pCode, String pName, Integer pPRICE, String brand, String color, Integer pAmount,
			String pOption, String iMAGE_PATH) {
		this.pCode = pCode;
		this.pName = pName;
		this.pPRICE = pPRICE;
		this.Brand = brand;
		this.Color = color;
		this.pAmount = pAmount;
		this.pOption = pOption;
		this.IMAGE_PATH = iMAGE_PATH;
	}

}
