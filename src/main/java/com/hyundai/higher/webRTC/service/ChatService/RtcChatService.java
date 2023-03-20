package com.hyundai.higher.webRTC.service.ChatService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.hyundai.higher.webRTC.dto.ChatRoomDto;
import com.hyundai.higher.webRTC.dto.ChatRoomMap;
import com.hyundai.higher.webRTC.dto.WebSocketMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 3. 15.
 * @FileName: RtcChatService.java
 * @author  : 박성환
 * @설명    : 화상채팅 서비스

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RtcChatService {


    public ChatRoomDto createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt) {
        ChatRoomDto room = ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .roomPwd(roomPwd) // 채팅방 패스워드
                .secretChk(secretChk) // 채팅방 잠금 여부
                .userCount(0) // 채팅방 참여 인원수
                .maxUserCnt(maxUserCnt) // 최대 인원수 제한
                .build();

        room.setUserList(new HashMap<String, WebSocketSession>());

        // msg 타입이면 ChatType.MSG
        room.setChatType(ChatRoomDto.ChatType.RTC);

        // map 에 채팅룸 아이디와 만들어진 채팅룸을 저장
        ChatRoomMap.getInstance().getChatRooms().put(room.getRoomId(), room);

        return room;
    }

    public Map<String, WebSocketSession> getClients(ChatRoomDto room) {
       
        Optional<ChatRoomDto> roomDto = Optional.ofNullable(room);

        return (Map<String, WebSocketSession>) roomDto.get().getUserList();
    }

    public Map<String, WebSocketSession> addClient(ChatRoomDto room, String name, WebSocketSession session) {
        Map<String, WebSocketSession> userList = (Map<String, WebSocketSession>) room.getUserList();
        userList.put(name, session);
        return userList;
    }

    // userList 에서 클라이언트 삭제
    public void removeClientByName(ChatRoomDto room, String userUUID) {
        room.getUserList().remove(userUUID);
    }

    // 유저 카운터 return
    public boolean findUserCount(WebSocketMessage webSocketMessage){
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(webSocketMessage.getData());
        log.info("ROOM COUNT : [{} ::: {}]",room.toString(),room.getUserList().size());
        return room.getUserList().size() > 1;
    }

}
