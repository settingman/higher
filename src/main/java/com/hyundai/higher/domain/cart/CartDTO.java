package com.hyundai.higher.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

	private String pcode;
	private Integer pAmount;
	private String poption;
	private String mid;
}
