package com.hyundai.higher.webRTC.rtc;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.higher.webRTC.dto.ChatRoomDto;
import com.hyundai.higher.webRTC.dto.ChatRoomMap;
import com.hyundai.higher.webRTC.dto.WebSocketMessage;
import com.hyundai.higher.webRTC.service.ChatService.ChatServiceMain;
import com.hyundai.higher.webRTC.service.ChatService.RtcChatService;

import lombok.RequiredArgsConstructor;

/**
 * @since   : 2023. 3. 15.
 * @FileName: SignalHandler.java
 * @author  : 박성환
 * @설명    : WEBRTC socket Handler

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 * </pre>
 */
@Component
@RequiredArgsConstructor
public class SignalHandler extends TextWebSocketHandler {

	private final RtcChatService rtcChatService;
	private final ChatServiceMain chatServiceMain;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ObjectMapper objectMapper = new ObjectMapper();

	// roomID to room Mapping
	private Map<String, ChatRoomDto> rooms = ChatRoomMap.getInstance().getChatRooms();

	// message types, used in signalling:
	// SDP Offer message
	private static final String MSG_TYPE_OFFER = "offer";
	// SDP Answer message
	private static final String MSG_TYPE_ANSWER = "answer";
	// New ICE Candidate message
	private static final String MSG_TYPE_ICE = "ice";
	// join room data message
	private static final String MSG_TYPE_JOIN = "join";
	// leave room data message
	private static final String MSG_TYPE_LEAVE = "leave";

	// 연결 끊어졌을 때 이벤트처리
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		logger.info("[ws] Session has been closed with status [{} {}]", status, session);
	}

	// 소켓 연결되었을 때 이벤트 처리
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		/*
		 * 웹 소켓이 연결되었을 때 클라이언트 쪽으로 메시지를 발송한다 이때 원본 코드에서는 rooms.isEmpty() 가 false 를 전달한다.
		 * 이 의미는 현재 room 에 아무도 없다는 것을 의미하고 따라서 추가적인 ICE 요청을 하지 않도록 한다.
		 *
		 * 현재 채팅 코드에서는 chatRoom 안에 userList 안에 user가 저장되기 때문에 rooms 이 아닌 userList 에 몇명이
		 * 있는지 확인해야 했다. 따라서 js 쪽에서 ajax 요청을 통해 rooms 가 아닌 userList 에 몇명이 있는지 확인하고 2명 이상인
		 * 경우에만 JS에서 이와 관련된 변수를 true 가 되도록 변경하였다.
		 *
		 * 이렇게 true 상태가 되면 이후에 들어온 유저가 방안에 또 다른 유저가 있음을 확인하고, P2P 연결을 시작한다.
		 */
		sendMessage(session,
				new WebSocketMessage("Server", MSG_TYPE_JOIN, Boolean.toString(!rooms.isEmpty()), null, null));
	}

	// 소켓 메시지 처리
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
		try {
			// 소켓쪽에서는 socket.send 로 메시지를 발송한다 => 참고로 JSON 형식으로 변환해서 전달해온다
			WebSocketMessage message = objectMapper.readValue(textMessage.getPayload(), WebSocketMessage.class);
			logger.debug("[ws] Message of {} type from {} received", message.getType(), message.getFrom());
			// 유저 uuid 와 roomID 를 저장
			String userUUID = message.getFrom(); // 유저 uuid
			String roomId = message.getData(); // roomId


			ChatRoomDto room;
			// 메시지 타입에 따라서 서버에서 하는 역할이 달라진다
			switch (message.getType()) {

			// 클라이언트에게서 받은 메시지 타입에 따른 signal 프로세스
			case MSG_TYPE_OFFER:
			case MSG_TYPE_ANSWER:
			case MSG_TYPE_ICE:
				Object candidate = message.getCandidate();
				Object sdp = message.getSdp();

				logger.debug("[ws] Signal: {}",
						candidate != null ? candidate.toString().substring(0, 64) : sdp.toString().substring(0, 64));

				ChatRoomDto roomDto = rooms.get(roomId);

				if (roomDto != null) {
					Map<String, WebSocketSession> clients = rtcChatService.getClients(roomDto);

				
					for (Map.Entry<String, WebSocketSession> client : clients.entrySet()) {

						// send messages to all clients except current user
						if (!client.getKey().equals(userUUID)) {
							// select the same type to resend signal
							sendMessage(client.getValue(),
									new WebSocketMessage(userUUID, message.getType(), roomId, candidate, sdp));
						}
					}
				}
				break;

			case MSG_TYPE_JOIN:
				logger.debug("[ws] {} has joined Room: #{}", userUUID, message.getData());


				room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

				rtcChatService.addClient(room, userUUID, session);

				chatServiceMain.plusUserCnt(roomId);

				rooms.put(roomId, room);
				break;

			case MSG_TYPE_LEAVE:
				logger.info("[ws] {} is going to leave Room: #{}", userUUID, message.getData());

				// roomID 기준 채팅방 찾아오기
				room = rooms.get(message.getData());

				
				Optional<String> client = rtcChatService.getClients(room).keySet().stream()
						.filter(clientListKeys -> Objects.equals(clientListKeys, userUUID))
						.findAny();

		
				client.ifPresent(userID -> rtcChatService.removeClientByName(room, userID));

				// 채팅방에서 떠날 시 유저 카운트 -1
				chatServiceMain.minusUserCnt(roomId);

				logger.debug("삭제 완료 [{}] ", client);
				break;

			
			default:
				logger.debug("[ws] Type of the received message {} is undefined!", message.getType());
			}

		} catch (IOException e) {
			logger.debug("An error occured: {}", e.getMessage());
		}
	}

	private void sendMessage(WebSocketSession session, WebSocketMessage message) {
		try {
			String json = objectMapper.writeValueAsString(message);
			session.sendMessage(new TextMessage(json));
		} catch (IOException e) {
			logger.debug("An error occured: {}", e.getMessage());
		}
	}
}
