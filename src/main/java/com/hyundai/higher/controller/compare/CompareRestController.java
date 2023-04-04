package com.hyundai.higher.controller.compare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.service.cart.CartService;

import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 04. 03.
 * @FileName: CompareRestController.java
 * @author : 박서현
 * @설명 : 
 * 
 * <pre>
 * 	   수정일          수정자                수정내용
 * -------------   --------    ---------------------------
 * 2023. 04. 03.     박서현       최초 생성
 * </pre>
 */

@Log4j2
@RequestMapping("/compare")
@RestController
public class CompareRestController {
	
	@Autowired
	private CartService cService;

}
