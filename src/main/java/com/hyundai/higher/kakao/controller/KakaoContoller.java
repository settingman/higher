package com.hyundai.higher.kakao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.hyundai.higher.kakao.service.KakaoService;

/**
 * @since   : 2023. 4. 8.
 * @FileName: KakaoContoller.java
 * @author  : 박성환
 * @설명    : 카카오톡 API RESTCONTROLLER

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 8.     박성환      	최초 생성
 * </pre>
 */
@RestController
@RequestMapping("/kakao")
public class KakaoContoller {

	@Autowired
	public KakaoService kakaoService;

	
	// 카카오톡 로그인
	@RequestMapping("/login")
    public RedirectView goKakaoOAuth() {
       return kakaoService.goKakaoOAuth();
    }

	@RequestMapping("/login-callback")
	public RedirectView loginCallback(@RequestParam("code") String code) {
	   return kakaoService.loginCallback(code);
	}	
	
	@RequestMapping("/profile")
    public String getProfile() {
       return kakaoService.getProfile();
    }	
	
	@RequestMapping("/authorize")
    public RedirectView goKakaoOAuth(@RequestParam("scope") String scope) {
		return kakaoService.goKakaoOAuth(scope);
    }	
	
	// 친구목록 불러오기
	@RequestMapping("/friends")
    public String getFriends() {
       return kakaoService.getFriends();
    }	
	
	// 카카오톡 나에게 메세지 보내기
	@RequestMapping("/message")
    public String message() {
       return kakaoService.message();
    }
	
	// 카카오톡 친구에게 메세지 보내기
	@RequestMapping("/sendMessage")
    public String sendMessage(String template) {
       return kakaoService.Sendmessage(template);
    }
	
	
	
}
