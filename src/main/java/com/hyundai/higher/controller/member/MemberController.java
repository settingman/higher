package com.hyundai.higher.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.domain.member.MemberFormDto;
import com.hyundai.higher.mapper.member.MemberMapper;
import com.hyundai.higher.service.member.MailServiceImpl;
import com.hyundai.higher.service.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 2. 24.
 * @FileName: MemberController.java
 * @author : 박성환
 * @설명 : 로그인, 회원가입 등 회원관련 기능 구현
 * 
 *     <pre>
 * 수정일           수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      회원가입, 로그인 기능 구현
 *     </pre>
 */

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

	private final MemberService memberService;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MailServiceImpl mailService;

	// 회원가입페이지 접근
	@GetMapping(value = "/joinform")
	public String memberForm(Model model) {

		model.addAttribute("memberFormDto", new MemberFormDto());

		return "member/joinform";
	}

	// 회원가입 진행
	@PostMapping(value = "joinform")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {

			for (ObjectError allError : bindingResult.getAllErrors()) {
				System.out.println(allError.toString());
			}
			log.info("bindingResult Error");
			return "member/joinform";
		}

		try {

			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
			log.info("try Error");
		} catch (IllegalStateException e) {
			log.info("catch Error");
			log.error("error:", e);
			model.addAttribute("errorMessage", e.getMessage());
			return "member/joininfoform";
		}

		return "redirect:/";
	}

	// 로그인
	@GetMapping("/login")
	public String login(@RequestParam(value = "mId", required = false) String mId,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception, Model model) {

		/* 에러와 예외를 모델에 담아 view resolve */
		model.addAttribute("mId", mId);
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "/member/login";

	}

	// 회원가입 완료
	@GetMapping(value = "/joincomplete")
	public String joincomplete(Model model) {
		return "/member/joincomplete";
	}

	// 로그인 에러페이지 접근
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "/member/login";
	}

	// Secuirty 로그아웃
	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response,
				SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/member/login";
	}

	// 아이디 찾기 페이지 요청
	@GetMapping(value = "/findId")
	public String findId(Model model) {

		return "/member/findId";
	}

	// 아이디 찾기 페이지 요청
	@GetMapping(value = "/findPw")
	public String findPw(Model model) {

		return "/member/findPw";
	}

	// find id - 아이디 찾기 기능
	@PostMapping(value = "/findId")
	public String findId(@RequestParam("mName") String mName, @RequestParam("mBirth") Integer mBirth, Model model) {

		log.info(mName);
		log.info(mBirth.toString());

		Member findMember = memberMapper.findByNameBirth(mName, mBirth.toString());

		if (findMember == null) {
			log.info("fail");
			return "/member/findId";
		}

		String mId = findMember.getMId();

		findMember.setMId(mId.replaceAll("(?<=.{3}).(?=.*@)", "*"));

		model.addAttribute("findMember", findMember);

		return "/member/findIdComplete";
	}

}
