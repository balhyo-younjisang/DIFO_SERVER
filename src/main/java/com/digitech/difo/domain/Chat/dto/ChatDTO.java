package com.digitech.difo.domain.Chat.dto;

import com.digitech.difo.domain.Chat.service.ChatService;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import com.digitech.difo.domain.Member.service.MemberService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

public class ChatDTO {
    @Getter
    public static class ChatRoomDTO {
        private String roomId;
        private String name;
        private Set<WebSocketSession> sessions = new HashSet<>();
        @Builder
        public ChatRoomDTO(String roomId, String name) {
            this.roomId = roomId;
            this.name = name;
        }

        public void handleActions(WebSocketSession session, ChatRoomMessageDTO chatMessage, ChatService chatService) {
            if (chatMessage.getType().equals(ChatRoomMessageDTO.MessageType.ENTER)) {
                sessions.add(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            }
            sendMessage(chatMessage, chatService);
        }

        public <T> void sendMessage(T message, ChatService chatService) {
            sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
        }
    }

    @Getter
    @Setter
    public static class ChatRoomMessageDTO {
        // 메시지 타입 : 입장, 채팅
        public enum MessageType {
            ENTER, TALK
        }
        private MessageType type; // 메시지 타입
        private String roomId; // 방번호
        private Long sender; // 메시지 보낸사람
        private String message; // 메시지
    }
}
