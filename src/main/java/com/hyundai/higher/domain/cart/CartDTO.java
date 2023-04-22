package com.hyundai.higher.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
//삭제 예정
	private String pcode;
	private Integer pAmount;
	private String poption;
	private String mid;
}
