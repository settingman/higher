package com.hyundai.higher.webrtcss.socket;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hyundai.higher.webrtcss.domain.Room;
import com.hyundai.higher.webrtcss.domain.RoomService;
import com.hyundai.higher.webrtcss.domain.WebSocketMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SignalHandler2Test {
    @Autowired private RoomService service;
    @Autowired private SignalHandler2 handler;

    private String name;
    private WebSocketSession session;
    private Room room;

    @Before
    public void setup() {
        Long id = 1L;
        name = UUID.randomUUID().toString();
        session = mock(WebSocketSession.class);
        room = new Room(id);
        service.addRoom(room);
    }

    @Test
    public void shouldRemoveClient_whenConnectionClosed() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        WebSocketMessage message = new WebSocketMessage(name,"join", room.getId().toString(), null, null);
        handler.handleTextMessage(session, new TextMessage(ow.writeValueAsString(message)));
        message = new WebSocketMessage(name, "leave", room.getId().toString(), null, null);
        handler.handleTextMessage(session, new TextMessage(ow.writeValueAsString(message)));
        handler.afterConnectionClosed(session, CloseStatus.NORMAL);

        assertThat(service.getClients(room))
                .isEmpty();
    }

    @After
    public void teardown() {
        name = null;
        session = null;
        room = null;
    }
}
