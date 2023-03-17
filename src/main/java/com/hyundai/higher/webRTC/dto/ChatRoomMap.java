package com.hyundai.higher.webRTC.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @since   : 2023. 3. 15.
 * @FileName: ChatRoomMap.java
 * @author  : 박성환
 * @설명    : 채팅방 싱글톤

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 * </pre>
 */
@Getter
@Setter
public class ChatRoomMap {
    private static ChatRoomMap chatRoomMap = new ChatRoomMap();
    private Map<String, ChatRoomDto> chatRooms = new LinkedHashMap<>();

//    @PostConstruct
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }

    private ChatRoomMap(){}

    public static ChatRoomMap getInstance(){
        return chatRoomMap;
    }

}
