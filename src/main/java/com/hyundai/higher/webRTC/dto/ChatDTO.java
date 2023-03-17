package com.hyundai.higher.webRTC.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @since   : 2023. 3. 15.
 * @FileName: ChatDTO.java
 * @author  : 박성환
 * @설명    : 채팅방_ 메세지 DTO

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 * </pre>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
	
    // 메시지  타입 : 입장, 채팅 
    public enum MessageType{
        ENTER, TALK, LEAVE;
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private String time; // 채팅 발송 시간간

    /* 파일 업로드 관련 변수 */
    private String s3DataUrl; // 파일 업로드 url
    private String fileName; // 파일이름
    private String fileDir; // s3 파일 경로
}
