package com.hyundai.higher.controller.beauty;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.beauty.Profile;
import com.hyundai.higher.mapper.beauty.BeautyMapper;
import com.hyundai.higher.webRTC.dto.ReservationDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 4. 1.
 * @FileName: BeautyController.java
 * @author  : 박성환
 * @설명    : 온라인뷰티 기능 컨트롤러

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 1.     박성환      	최초 생성
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/beauty")
@Controller
public class BeautyController {

	private final BeautyMapper beautyMapper;

	// 온라인 예약 내역 불러오기
	@GetMapping("/reservation")

	public String getReservation(Model model) {

		List<ReservationDTO> reservations = beautyMapper.findReservation();

		model.addAttribute("reservations", reservations);

		return "admin/reservation";

	}

}
