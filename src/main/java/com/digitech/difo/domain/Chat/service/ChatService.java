package com.digitech.difo.domain.Chat.service;

import com.digitech.difo.domain.Chat.dto.ChatDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatDTO.ChatRoomDTO> chatRooms;

    @PostConstruct
    private void init() {
        System.out.println("init");
        chatRooms = new LinkedHashMap<>();
    }

    /**
     * 채팅방을 모두 찾아 리턴
     * @return
     */
    public List<ChatDTO.ChatRoomDTO> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    /**
     * 채팅방 ID를 통해 방을 찾는 메서드
     * @param roomId
     * @return
     */
    public ChatDTO.ChatRoomDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    /**
     * 채팅방을 만드는 메서드
     * @param name
     * @return
     */
    public ChatDTO.ChatRoomDTO createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatDTO.ChatRoomDTO chatRoom = ChatDTO.ChatRoomDTO.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
