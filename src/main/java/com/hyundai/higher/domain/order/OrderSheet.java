package com.hyundai.higher.domain.order;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @since   : 2023. 3. 23.
 * @FileName: OrderDTO.java
 * @author  : 박성환
 * @설명    : 주문내역서 DTO

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 23.     박성환      	최초 생성
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderSheet {

	private String orderId;
	private String oName;
	
	private String oDate;
	

	
	private Integer oZipcode;
	private String oAddress1;
	private String oAddress2;

	private String oReceiver;
	private String oTel;
	private String oPayment;
	
	private Integer oTotalPrice;
	
	private List<String> product_id;
	private List<Integer> product_Quntity;
	private List<String> product_name;
	private List<String> product_image;
	private List<Integer> product_price;
	
	


}
