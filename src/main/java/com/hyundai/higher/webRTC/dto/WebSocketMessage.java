package com.hyundai.higher.webRTC.dto;

import lombok.*;



/**
 * @since   : 2023. 3. 17.
 * @FileName: WebSocketMessage.java
 * @author  : 박성환
 * @설명    : WebRTC 연결 시 사용

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 17.     박성환      	최초 생성
 * </pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage {
    private String from; // 보내는 유저 UUID
    private String type; // 메시지 타입
    private String data; // roomId
    private Object candidate; // 상태
    private Object sdp; // sdp 정보
}
