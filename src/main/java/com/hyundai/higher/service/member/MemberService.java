package com.hyundai.higher.service.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.mapper.member.MemberMapper;
import com.hyundai.higher.security.dto.SecurityMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 2. 24.
 * @FileName: MemberService.java
 * @author : 박성환
 * @설명 : 회원 과련 기능 구현
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      		회원서비스 정의
 *     </pre>
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements UserDetailsService {

	private final MemberMapper memberMapper;

	// 회원 저장
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		memberMapper.save(member);

		return memberMapper.findById(member.getMId());
	}

	// 회원가입 중복확인
	public void validateDuplicateMember(Member member) {
		Member findMember = memberMapper.findById(member.getMId());

		if (findMember != null) {
			log.info("이미 가입된 회원");
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}

		log.info("가입안된 회원");
	}

	// Security User 생성
	@Override
	public UserDetails loadUserByUsername(String mId) throws UsernameNotFoundException {

		Member member = memberMapper.findById(mId);

		log.info("userdetails");

		if (member == null) {
			log.info("No user");
			throw new UsernameNotFoundException(mId);

		}
		log.info("-------------------");
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getMRole()));

		log.info(member.getMPassword());
		SecurityMember securityMember = new SecurityMember(member.getMId(), member.getMPassword(), authorities);
		log.info(securityMember.toString());

		// 시큐리티 회원정보 주입. (다른 정보를 더 넣고싶다면 Secuirty 맴버에 필드 추가 후 주입해준다.)
		securityMember.setMName(member.getMName());
		securityMember.setMPhone(member.getMPhone());

		log.info(securityMember.toString());

		return securityMember;

	}

}