package com.hyundai.higher.domain.cart;

import java.sql.Date;

import com.hyundai.higher.domain.member.MemberRole;

import lombok.AllArgsConstructor;
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
 * 2023. 3. 28. 	박성환			lombok 오류로 2번째 글자가 대문자일 때 
 * 									매칭안되는 오류로 인한 속성값 변경
 *     </pre>
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderItem {

	private String pcode;
	private String pname;
	private Integer pprice;
	private String brand;
	private String color;
	private Integer pamount;
	private String poption;
	private String IMAGE_PATH;

}
