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

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/beauty")
@Controller
public class BeautyController {

	private final BeautyMapper beautyMapper;

	// 회원가입 아이디 중복 체크 AJAX
	@GetMapping("/reservation")

	public String getReservation(Model model) {

		List<ReservationDTO> reservations = beautyMapper.findReservation();

		model.addAttribute("reservations", reservations);

		return "admin/reservation";

	}

}
