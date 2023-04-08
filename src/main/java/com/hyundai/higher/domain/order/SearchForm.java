package com.hyundai.higher.domain.order;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @since   : 2023. 4. 08.
 * @FileName: searchForm.java
 * @author  : 박성환
 * @설명    : 주문검색

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 08.     박성환      	최초 생성
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchForm {
	
	private String searchType;
	private String ordStrtDt;
	private String ordEndDt;
	private String itemNm;
	private String listSize;
	private String page;
	private String ordNo;
	
	
	


}
