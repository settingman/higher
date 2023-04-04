package com.hyundai.higher.controller.beauty;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.beauty.Profile;
import com.hyundai.higher.mapper.beauty.BeautyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/beauty")
@RestController
public class BeautyRestController {

	private final BeautyMapper beautyMapper;

	// 회원가입 아이디 중복 체크 AJAX
	@GetMapping("/profile")

	public Profile getProfile(@RequestParam("mid") String mid, @RequestParam("rid") String rid) {

		Profile profile = beautyMapper.findProfile(mid, rid);

		return profile;

	}

}
