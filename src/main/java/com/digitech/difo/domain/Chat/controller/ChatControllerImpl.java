package com.digitech.difo.domain.Chat.controller;

import com.digitech.difo.domain.Chat.dto.ChatDTO;
import com.digitech.difo.domain.Chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatControllerImpl implements ChatController {
    private final ChatService chatService;


    @Override
    public ChatDTO.ChatRoomDTO createRoom(String name) {
        return chatService.createRoom(name);
    }

    @Override
    public List<ChatDTO.ChatRoomDTO> findAllRoom() {
        return chatService.findAllRoom();
    }
}
