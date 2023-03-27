package com.hyundai.higher.controller.makeup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @since   : 2023. 3. 27.
 * @FileName: ReservationController.java
 * @author  : 이세아
 * @설명    : @

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 27.     이세아       create
 * 2023. 3. 27.     이세아       예약서비스 html 연결
 * </pre>
 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reserv")
@Controller
public class ReservationController {
	
	@GetMapping("/reserv_main")
	public void reserv_main() {
		log.info("==== Make-on 메인 페이지====");
	}

	@GetMapping("/reserv_type")
	public void reserv_type() {
		log.info("==== Make-on 예약 타입 선정 ====");
	}

	@GetMapping("/reserv_offline")
	public void reserv_offline() {
		log.info("==== 예약 디테일 입력 페이지 : 오프라인 전용 ====");
	}

	@GetMapping("/reserv_online")
	public void reserv_online() {
		log.info("==== 예약 디테일 입력 페이지 : 온라인 전용 ====");
	}

}
