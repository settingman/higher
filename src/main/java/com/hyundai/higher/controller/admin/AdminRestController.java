package com.hyundai.higher.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.beauty.Profile;
import com.hyundai.higher.mapper.beauty.BeautyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 4. 1.
 * @FileName: BeautyRestController.java
 * @author  : 박성환
 * @설명    : 온라인 뷰티 RESTCONTROLLER

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 1.     박성환      	최초 생성
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminRestController {

	private final BeautyMapper beautyMapper;

	// 온라인 예약 회원 프로필 점검
	@GetMapping("/profile")

	public Profile getProfile(@RequestParam("mid") String mid, @RequestParam("rid") String rid) {

		Profile profile = beautyMapper.findProfile(mid, rid);

		return profile;

	}

}
