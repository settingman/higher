package com.hyundai.higher.webRTC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
@Slf4j
public class MainController {

	private final ChatServiceMain chatServiceMain;

	@GetMapping("/")
	public String goChatRoom(Model model) {

		model.addAttribute("list", chatServiceMain.findAllRoom());

		log.debug("SHOW ALL ChatList {}", chatServiceMain.findAllRoom());
		return "roomlist";
	}

}
