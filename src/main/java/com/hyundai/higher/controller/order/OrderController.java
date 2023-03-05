package com.hyundai.higher.controller.order;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.domain.member.MemberFormDto;
import com.hyundai.higher.mapper.member.MemberMapper;
import com.hyundai.higher.service.member.MailServiceImpl;
import com.hyundai.higher.service.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 3. 3.
 * @FileName: OrderController.java
 * @author : 박성환
 * @설명 : 오더 서비스 구현.
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 3.     박성환      최초 생성
 *     </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
public class OrderController {

}
