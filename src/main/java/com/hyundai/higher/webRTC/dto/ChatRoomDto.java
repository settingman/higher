package com.hyundai.higher.webRTC.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



/**
 * @since   : 2023. 3. 15.
 * @FileName: ChatRoomDto.java
 * @author  : 박성환
 * @설명    : 채팅방 DTO

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 * </pre>
 */
@Data
@Builder
@EqualsAndHashCode
@Getter
@Setter
public class ChatRoomDto {
    @NotNull
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름 
    private int userCount; // 채팅방 인원수
    private int maxUserCnt; // 채팅방 최대 인원 제한

    private String roomPwd; // 채팅방 삭제시 필요한 pwd
    private boolean secretChk; // 채팅방 잠금 여부
    public enum ChatType{  // 화상 채팅, 문자 채팅
        MSG, RTC
    }
    private ChatType chatType; //  채팅 타입 여부

    private Map<String, ?> userList;

}
