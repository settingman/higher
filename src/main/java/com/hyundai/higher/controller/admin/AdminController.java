package com.hyundai.higher.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.mapper.beauty.BeautyMapper;
import com.hyundai.higher.mapper.member.MemberMapper;
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
 * 2023. 4. 8.     박성환      	회원 리스트 조회 
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

	private final BeautyMapper beautyMapper;
	private final MemberMapper memberMapper;

	// 온라인 예약 내역 불러오기
	@GetMapping("/reservation")

	public String getReservation(Model model) {

		List<ReservationDTO> reservations = beautyMapper.findReservation();

		model.addAttribute("reservations", reservations);

		return "admin/reservation";

	}
	
	// 회원 리스트 불러오기
	@GetMapping("/memberlist")

	public String findMember(Model model) {

		List<Member> members = memberMapper.findMember();

		model.addAttribute("members", members);

		return "admin/memberlist";

	}
	
	
	

}
