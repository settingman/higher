package com.hyundai.higher.domain.cart;

import lombok.Data;

@Data
public class CartDTO {

	private String pcode;
	private Integer pamount;
	private String optname;
	private String mid;
}
