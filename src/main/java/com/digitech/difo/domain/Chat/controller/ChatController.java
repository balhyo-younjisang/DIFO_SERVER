package com.digitech.difo.domain.Chat.controller;

import com.digitech.difo.domain.Chat.dto.ChatDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/chat")
public interface ChatController {
    @PostMapping
    public ChatDTO.ChatRoomDTO createRoom(@RequestParam String name);

    @GetMapping
    public List<ChatDTO.ChatRoomDTO> findAllRoom();
}
