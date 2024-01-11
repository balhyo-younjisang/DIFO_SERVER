package com.digitech.difo.global.config;

import com.digitech.difo.domain.Chat.dto.ChatDTO;
import com.digitech.difo.domain.Chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        ChatDTO.ChatRoomMessageDTO messageDTO = objectMapper.readValue(payload, ChatDTO.ChatRoomMessageDTO.class);
        ChatDTO.ChatRoomDTO chatRoomDTO = chatService.findRoomById(messageDTO.getRoomId());
        chatRoomDTO.handleActions(session, messageDTO, chatService);
    }
}