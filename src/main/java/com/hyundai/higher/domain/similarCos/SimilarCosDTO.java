package com.hyundai.higher.domain.similarCos;

import lombok.Data;

@Data
public class SimilarCosDTO {

	private String pcode;
	
	private int total;
	private int percent;
	
	private String pname;
	private String iloc;
	private int bno;
	private String bname;
	private int pprice;
	
	private double rates;
	
}
