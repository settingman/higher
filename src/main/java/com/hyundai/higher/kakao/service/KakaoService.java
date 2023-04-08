package com.hyundai.higher.kakao.service;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonParser;
import com.hyundai.higher.kakao.common.Const;
import com.hyundai.higher.kakao.common.Trans;

import lombok.RequiredArgsConstructor;

/**
 * @since : 2023. 4. 8.
 * @FileName: KakaoService.java
 * @author : 박성환
 * @설명 : 카카오톡 API 요청 처리
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 8.     박성환      	최초 생성
 *     </pre>
 */
@RequiredArgsConstructor
@Service
public class KakaoService {

	private final HttpSession httpSession;

	@Autowired
	public HttpCallService httpCallService;

	@Value("${rest-api-key}")
	private String REST_API_KEY;

	@Value("${redirect-uri}")
	private String REDIRECT_URI;

	@Value("${authorize-uri}")
	private String AUTHORIZE_URI;

	@Value("${token-uri}")
	public String TOKEN_URI;

	@Value("${client-secret}")
	private String CLIENT_SECRET;

	@Value("${kakao-api-host}")
	private String KAKAO_API_HOST;

	public RedirectView goKakaoOAuth() {
		return goKakaoOAuth("");
	}

	public RedirectView goKakaoOAuth(String scope) {

		String uri = AUTHORIZE_URI + "?redirect_uri=" + REDIRECT_URI + "&response_type=code&client_id=" + REST_API_KEY;
		if (!scope.isEmpty())
			uri += "&scope=" + scope;

		return new RedirectView(uri);
	}

	//카카오톡 request 콜백 처리 (토큰발급)
	public RedirectView loginCallback(String code) {
		String param = "grant_type=authorization_code&client_id=" + REST_API_KEY + "&redirect_uri=" + REDIRECT_URI
				+ "&client_secret=" + CLIENT_SECRET + "&code=" + code;
		String rtn = httpCallService.Call(Const.POST, TOKEN_URI, Const.EMPTY, param);
		httpSession.setAttribute("token", Trans.token(rtn, new JsonParser()));
		return new RedirectView("/test4");
	}

	
	//카카오톡 프필 받기
	public String getProfile() {
		String uri = KAKAO_API_HOST + "/v2/user/me";
		return httpCallService.CallwithToken(Const.GET, uri, httpSession.getAttribute("token").toString());
	}

	
	// 카카오톡 친구 리스트 불러오기
	public String getFriends() {
		String uri = KAKAO_API_HOST + "/v1/api/talk/friends";
		return httpCallService.CallwithToken(Const.GET, uri, httpSession.getAttribute("token").toString());
	}

	public String message() {
		String uri = KAKAO_API_HOST + "/v2/api/talk/memo/default/send";
		return httpCallService.CallwithToken(Const.POST, uri, httpSession.getAttribute("token").toString(),
				Trans.default_msg_param);
	}

	
	// 카카오톡 친구에게 메세지 보내기
	public String Sendmessage() {

		String uri2 = KAKAO_API_HOST + "/v1/api/talk/friends";

		String uri = KAKAO_API_HOST + "/v1/api/talk/friends/message/scrap/send";

		String uuid = httpCallService.CallwithToken(Const.GET, uri2, httpSession.getAttribute("token").toString());

		String[] uuids = uuid.split(",");

		String friends = "receiver_uuids=%5B%22" + uuids[0] + "%22%2C%22" + uuids[1] + "%22%2C%22" + uuids[2]
				+ "%22%5D&request_url=https%3A%2F%2Flocalhost%3A8443&template_id=92317";

		return httpCallService.CallwithToken(Const.POST, uri, httpSession.getAttribute("token").toString(), friends);
	}
}
