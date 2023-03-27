package com.hyundai.higher.webRTC.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyundai.higher.webRTC.dto.ChatRoomDto;
import com.hyundai.higher.webRTC.dto.ChatRoomMap;
import com.hyundai.higher.webRTC.service.ChatService.ChatServiceMain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since   : 2023. 3. 15.
 * @FileName: ChatRoomController.java
 * @author  : 박성환
 * @설명    : ROOM Controller

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     박성환      	최초 생성
 * </pre>
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatServiceMain chatServiceMain;

    // 채팅방 생성
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr) {
    	
        ChatRoomDto room;

        room = chatServiceMain.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);


        log.info("CREATE Chat Room [{}]", room);

        rttr.addFlashAttribute("roomName", room);
        return "redirect:/rtc/room";
    }

    // 채팅방 입장 화면
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId){

        log.info("roomId {}", roomId);

       

        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        model.addAttribute("room", room);


        if (ChatRoomDto.ChatType.MSG.equals(room.getChatType())) {
            return "webRTC/chatroom";
        }else{
            model.addAttribute("uuid", UUID.randomUUID().toString());

            return "webRTC/rtcroom";
        }
    }

    // 채팅방 비밀번호 확인
    @GetMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd){
    	
    	log.info(roomPwd);

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        return chatServiceMain.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/del	Room/{roomId}")
    public String delChatRoom(@PathVariable String roomId){

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatServiceMain.delChatRoom(roomId);

        return "redirect:/";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return chatServiceMain.chkRoomUserCnt(roomId);
    }
}
