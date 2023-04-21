package com.hyundai.higher.domain.review;

import lombok.Data;

@Data
public class ReviewDTO {

	private String mid;
	private String mbti;
	
	private int rno;
	private int rrate;
	private String rdate;
	private String rcontent;
	private String pcode;
	private String oid;
	private String rtitle;
	
	private String pname;
	private String bname;

}