package com.hyundai.higher.webRTC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hyundai.higher.webRTC.service.ChatService.ChatServiceMain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

	private final ChatServiceMain chatServiceMain;

	// 채팅 리스트 화면
	// / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return

	// 스프링 시큐리티의 로그인 유저 정보는 Security 세션의 PrincipalDetails 안에 담긴다
	// 정확히는 PrincipalDetails 안에 ChatUser 객체가 담기고, 이것을 가져오면 된다.
	@GetMapping("/")
	public String goChatRoom(Model model) {

		model.addAttribute("list", chatServiceMain.findAllRoom());

//        model.addAttribute("user", "hey");
		log.debug("SHOW ALL ChatList {}", chatServiceMain.findAllRoom());
		return "roomlist";
	}

}
