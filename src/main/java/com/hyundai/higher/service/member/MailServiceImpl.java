package com.hyundai.higher.service.member;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.higher.domain.mail.MailVo;
import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.mapper.member.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 2. 3.
 * @FileName: MailServiceImpl.java
 * @author : 박성환
 * @설명 : 메일서비스 기능 구현
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      메일 시스템, 임시 비밀번호 전송 및 업데이트
 *     </pre>
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl {

	private final JavaMailSender mailSender;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	private static final String title = "현대백화점 임시 비밀번호 안내 이메일입니다.";
	private static final String message = "안녕하세요. 현대백화점 임시 비밀번호 안내 메일입니다. " + "\n"
			+ "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요." + "\n";

	public static final String key = createKey();

	private static final String fromAddress = "iwantthappy@gmail.com";

	/** 이메일 생성 **/
	public MailVo createMail(String tmpPassword, String memberEmail) {

		MailVo mailVo = MailVo.builder().toAddress(memberEmail).title(title).message(message + tmpPassword)
				.fromAddress(fromAddress).build();

		log.info("메일 생성 완료");
		return mailVo;
	}

	public MailVo createVaildMail(String memberEmail) {
		

		String eTitle = "현대백화점 이메일 인증 안내 이메일입니다.";
		String msgg = "안녕하세요. 현대백화점 이메일인증 안내 메일입니다. " + "\n"
				+ "회원님의 인증번호는 아래와 같습니다. " + "\n" + key;
	

		MailVo mailVo = MailVo.builder().toAddress(memberEmail).title(eTitle).message(msgg )
				.fromAddress(fromAddress).build();

		log.info("메일 생성 완료");
		return mailVo;
	}

	/** 이메일 전송 **/
	public void sendMail(MailVo mailVo) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mailVo.getToAddress());
		mailMessage.setSubject(mailVo.getTitle());
		mailMessage.setText(mailVo.getMessage());
		mailMessage.setFrom(mailVo.getFromAddress());
		mailMessage.setReplyTo(mailVo.getFromAddress());

		mailSender.send(mailMessage);

		log.info("메일 전송 완료");
	}

	public String sendVaildMail(MailVo mailVo) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mailVo.getToAddress());
		mailMessage.setSubject(mailVo.getTitle());
		mailMessage.setText(mailVo.getMessage());
		mailMessage.setFrom(mailVo.getFromAddress());
		mailMessage.setReplyTo(mailVo.getFromAddress());

		mailSender.send(mailMessage);

		log.info("메일 전송 완료");

		return key;
	}

	// 이메일 인증키 생성
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				// A~Z
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				// 0~9
				break;
			}
		}

		return key.toString();
	}

	// 임시비밀번호 생성
	public String getTmpPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		String pwd = "";

		/* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			pwd += charSet[idx];
		}

		log.info("임시 비밀번호 생성");

		return pwd;
	}

	/** 임시 비밀번호로 업데이트 **/
	public void updatePassword(String tmpPassword, String mId) {

		String encryptPassword = passwordEncoder.encode(tmpPassword);
		Member member = memberMapper.findById(mId);

		memberMapper.updatePassword(mId, encryptPassword);
		log.info("임시 비밀번호 업데이트");
	}
}