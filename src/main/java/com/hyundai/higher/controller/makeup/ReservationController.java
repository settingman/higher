package com.hyundai.higher.controller.makeup;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.service.makeup.ReservService;

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
 * 2023. 3. 30.		이세아	   첨부파일 업로드 처리
 * </pre>
 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reserv")
@Controller
public class ReservationController {
	
	@Autowired
	private ReservService service;
	
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
	
	@GetMapping("/uploadEx")
	public void uploadEx() {
		log.info("==== 파일 업로드 샘플 페이지 ====");
	}
	
	@PostMapping("/reserv.do")
	public String reservSend(@RequestParam("rimg") String rimg, @RequestParam("rdate") String rdate, Principal principal) {

		log.info("==== 예약 DB 처리중 ====");
		ReservVO vo = new ReservVO();
		vo.setMid(principal.getName());
		vo.setRdate(rdate);
		vo.setRimg(rimg);
		
		service.reserv(vo);
		log.info(vo);
		
		return "redirect:/mypage/mypage_reserv";
	}

}