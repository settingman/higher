package com.hyundai.higher.webRTC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @since : 2023. 3. 15.
 * @FileName: WebSocketConfig.java
 * @author : 박성환
 * @설명 : 텍스트 채팅방 config
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 *     </pre>
 */
@Configuration
@EnableWebSocketMessageBroker // 문자 채팅용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// 웹 소켓 연결을 위한 엔드포인트 설정 및 stomp sub/pub 엔드포인트 설정
	// setAllowedOriginPatterns("*") -> cors 해결
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 메시지를 구독하는 요청 url => 메시지 받을 때
		registry.enableSimpleBroker("/sub");

		// 메시지를 발행하는 요청 url => 메시지 보낼 때
		registry.setApplicationDestinationPrefixes("/pub");
	}

}
