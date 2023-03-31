package com.hyundai.higher.domain.cart;

import java.util.List;

import com.hyundai.higher.domain.product.OptionDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
 * 2023. 3. 28.		박서현		mid 추가
 * 2323. 3. 30.		박서현		optionList 추가
 *     </pre>
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartItem {

	private String pcode;
	private String pname;
	private Integer pprice;
	private String brand;
	private String color;
	private Integer pamount;
	private String poption;
	private String IMAGE_PATH;
	private String mid;
	
	private List<OptionDTO> optionList;
	
	public CartItem(String pcode, String pname, Integer pprice, String brand, String color, Integer pamount,
			String poption, String iMAGE_PATH, String mid) {
		this.pcode = pcode;
		this.pname = pname;
		this.pprice = pprice;
		this.brand = brand;
		this.color = color;
		this.pamount = pamount;
		this.poption = poption;
		this.IMAGE_PATH = iMAGE_PATH;
		this.mid = mid;
	}

	

}
