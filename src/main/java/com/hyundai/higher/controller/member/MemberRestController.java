package com.hyundai.higher.controller.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.higher.domain.mail.MailVo;
import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.mapper.member.MemberMapper;
import com.hyundai.higher.service.member.MailServiceImpl;
import com.hyundai.higher.service.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 2. 24.
 * @FileName: MemberRestController.java
 * @author : 박성환
 * @설명 : Member RestController
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      최초생성
 * 2023. 3. 15.     박성환      이메일 체크 기능 추가
 *     </pre>
 */



@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberRestController {

	private final MemberService memberService;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MailServiceImpl mailService;

	// 회원가입 아이디 중복 체크 AJAX
	@GetMapping("/check")
	
	public String checkId(@RequestParam("checkid") String checkId) {
		
		log.info(checkId);

		String result = "1";

		Member member = memberMapper.findById(checkId);

		if (member == null)
			result = "0";

		return result;

	}

	// 비밀번호 찾기 AJAX
	@GetMapping(value = "/ajaxfindPw")
	public String findIdPwPage(@RequestParam("mName") String mName, @RequestParam("mId") String mId, Model model) {

		log.info(mName);
		log.info(mId);

		Member findMember = memberMapper.findByNameId(mName, mId);

		if (findMember == null) {
			log.info("fail");
			return "fail";
		}

		String tmpPassword = mailService.getTmpPassword();

		/** 임시 비밀번호 저장 **/
		mailService.updatePassword(tmpPassword, mId);

		/** 메일 생성 & 전송 **/
		MailVo mail = mailService.createMail(tmpPassword, mId);

		mailService.sendMail(mail);

		log.info("임시 비밀번호 전송 완료");

		return "S";
	}
	
	
	// 회원가입 이메일 중복 체크
	@GetMapping(value = "/vaildEmail")
	public String vaildEmail(@RequestParam("emailAdr") String emailAdr) {

		MailVo mail = mailService.createVaildMail(emailAdr);
		String result = mailService.sendVaildMail(mail);

		return result;

	}
	
	
	
	
	
	
}
