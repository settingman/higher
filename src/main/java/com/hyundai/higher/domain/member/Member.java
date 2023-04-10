package com.hyundai.higher.domain.member;

import java.sql.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @since : 2023. 2. 24.
 * @FileName: Member.java
 * @author : 박성환
 * @설명 : 데이터베이스 Membe Table 주입 객체
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      DateBase Member Object
 *     </pre>
 * 
 *     t
 */
@NoArgsConstructor
@Getter
@Setter
public class Member {

	private String mId;

	private String mPassword;

	private String mName;

	private String mPhone;

	private String mEmail;

	private Date mBirth;

	private MemberRole mRole;
	
	private String mbti;

	@Builder
	public Member(String mId, String mPassword, String mName, String mPhone, String mEmail, Date mBirth,
			MemberRole mRole,String mbti) {
		this.mId = mId;
		this.mPassword = mPassword;
		this.mName = mName;
		this.mPhone = mPhone;
		this.mEmail = mEmail;
		this.mBirth = mBirth;
		this.mRole = mRole;
		this.mbti = mbti;
	}

	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {

		String date = memberFormDto.getMBirth().toString();
		String sqldate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);

		Member member = Member.builder().mId(memberFormDto.getMId())
				.mPassword(passwordEncoder.encode(memberFormDto.getMPassword())) // 암호화처리
				.mName(memberFormDto.getMName()).mPhone(memberFormDto.getMPhone()).mEmail(memberFormDto.getMEmail())
				.mBirth(Date.valueOf(sqldate)).mRole(MemberRole.USER).build();
		return member;
	}

	public void updatePassword(String password) {
		this.mPassword = password;
	}

}
