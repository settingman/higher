package com.hyundai.higher.webRTC.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.hyundai.higher.webRTC.dto.ChatRoomDto.ChatType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @since   : 2023. 3. 21.
 * @FileName: ReservationDTO.java
 * @author  : 박성환
 * @설명    : 백오피스 예약자 목록

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 21.     박성환      	최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString
public class ReservationDTO {
	
	
    private String roomId; // 채팅방 아이디(URL)
    
    private String memberName; // 고객명
    private String memberId; // 고객 아이디
    private boolean roomChk; // 채팅방 생성 여부
    private String resDate; // 예약 날짜
    private String resTime; // 예약 시간

	

}
