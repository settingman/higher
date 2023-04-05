package com.hyundai.higher.webRTC.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyundai.higher.mapper.beauty.BeautyMapper;
import com.hyundai.higher.webRTC.dto.ReservationDTO;
import com.hyundai.higher.webRTC.service.ChatService.ChatServiceMain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 3. 15.
 * @FileName: MainController.java
 * @author  : 박성환
 * @설명    : WEBRTC mainController

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 * </pre>
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/rtc")
@Slf4j
public class MainController {

	private final ChatServiceMain chatServiceMain;
	private final BeautyMapper beautyMapper;

	@GetMapping("/room")
	public String goChatRoom(Model model) {

		model.addAttribute("list", chatServiceMain.findAllRoom());
		
		List<ReservationDTO> reservations = beautyMapper.findTodayReservation();
		
		model.addAttribute("reservations", reservations);
		
		log.debug("SHOW ALL ChatList {}", chatServiceMain.findAllRoom());
		return "webRTC/roomlist";
	}

}
