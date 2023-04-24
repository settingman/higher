package com.hyundai.higher.domain.makeup;

import lombok.Data;

/**
 * @since   : 2023. 4. 8.
 * @FileName: ReservVO.java
 * @author  : 이세아
 * @설명    : 예약내역을 조회하기 위한 VO

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 8.     이세아       create
 * </pre>
 */


@Data
public class ReservVO {
	
	private String rid;
	private int rcheck;
	private String rimg;
	private String roomid;
	private String rdate;
	private String mid;
	private String rneeds;

}
