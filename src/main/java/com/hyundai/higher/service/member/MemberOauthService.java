package com.hyundai.higher.service.member;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.domain.member.MemberRole;
import com.hyundai.higher.mapper.member.MemberMapper;
import com.hyundai.higher.security.dto.SecurityMember;

import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 2. 24.
 * @FileName: MemberOauthService.java
 * @author : 박성환
 * @설명 : 소셜로그인 회원 처리
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      소셜로그인 서비스(구글)
 *     </pre>
 */
@Slf4j
@Service
public class MemberOauthService extends DefaultOAuth2UserService {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 구글 사용자 데이터베이스 저장
	private Member saveSocialMember(String mId, String clienName) throws SQLException {
		log.info("saveSocialMember 시작");
		// 기본에 동일한 이메일로 가입한 회원인지 확인
		Member socialMember = memberMapper.findById(mId);

		// 기본 회원이면 회원정보 반환
		if (!(socialMember == null)) {
			log.info("기존 회원");
			return socialMember;
		}

		// 가입한적이 없다면 추가 패스워드 1111 이름은 이메일주소
		socialMember = Member.builder().mId(mId).mPassword(passwordEncoder.encode("1111")) // 암호화처리
				.mName(clienName).mPhone("google").mEmail(mId).mBirth(new Date(230101)).mRole(MemberRole.USER).build();

		memberService.saveMember(socialMember);

		return socialMember;
	}

	// 구글로그인 사용자 -> SecurityMember
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("-------loaduser-------------");
		log.info("userRequest" + userRequest.toString());

		String clienName = userRequest.getClientRegistration().getClientName();
		String provider = userRequest.getClientRegistration().getRegistrationId();

		log.info(clienName);
		log.info(provider);
		

		// 인증 제공자 출력
		log.info("clienName " + clienName);
		log.info(userRequest.getAdditionalParameters().toString());
		
		 

		// 사용자 정보 가져오기 구글에서 허용한 API 범위
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("======oAuth2User===============");
		oAuth2User.getAttributes().forEach((k, v) -> {
			log.info(k + " : " + v);
		});
		log.info("======oAuth2User===============");

		// 신규회원 테이블에 저장 시작
		String email = null;
		String name = null;
		if (clienName.equals("Google")) {
			email = oAuth2User.getAttribute("email");
			log.info("구글 인증 확인");
			log.info("email : " + email);
		} else if (clienName.equals("Kakao")) {
			
			log.info("카카오 인증 확인");

			
			
			LinkedHashMap Account =oAuth2User.getAttribute("kakao_account");
			LinkedHashMap profile = (LinkedHashMap) Account.get("profile");
			
			email = Account.get("email").toString();
			name = profile.get("nickname").toString();
			
			
			log.info("카카오 인증 확인");
		

		}

		try {
			Member socialMember = saveSocialMember(email,clienName);

			log.info("---saveSocialMember--");
			log.info(socialMember.toString());

			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + socialMember.getMRole()));
			SecurityMember securityMember = new SecurityMember(socialMember.getMId(), socialMember.getMPassword(),
					authorities, oAuth2User.getAttributes());

			securityMember.setMName(socialMember.getMName());
			securityMember.setPassword(socialMember.getMPassword());

			// clubAuthMemberDTO --> UserDetails 반환
			log.info("OAuth2User 를 clubAuthMemberDTO");
			log.info(securityMember.toString());
			return securityMember;

		} catch (SQLException e) {
			log.info("saveSocialMember error");
			log.info("에러 ");
			log.info(e.toString());
		}

		// 구글에서 정보 가져온 oAuth2User
		return oAuth2User;
	}
	
	
	 public String getAccessToken(String code) throws JsonProcessingException {
		 
		 String REQUEST_URL = "https://kauth.kakao.com/oauth/token";
		 RestTemplate restTemplate=new RestTemplate();
		 
	        // HTTP Header 생성
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-type", "application/x-www-form-urlencoded");
	        headers.add("Accept", "application/json"); 

	        // HTTP Body 생성
	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("grant_type", "authorization_code");
	        params.add("client_id", "e094422f425dd0498e91f2deb839c9f7");
	        params.add("redirect_uri", "https://localhost:8080/member/oauth2/code/kakao");
	        params.add("code", code);     

	        
	        log.info(code);
	        log.info("토큰발급");
	        
	        
	        // HTTP 요청 보내기
	        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
	        
	        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(REQUEST_URL, kakaoTokenRequest, String.class);
	     
	        
	        log.info("토큰발급");
	        
	        

	        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
	        String responseBody = stringResponseEntity.getBody();
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(responseBody);
	        return jsonNode.get("access_token").asText();
	    }
	}
