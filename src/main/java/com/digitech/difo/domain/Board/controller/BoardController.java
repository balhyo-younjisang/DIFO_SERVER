package com.digitech.difo.domain.Board.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.digitech.difo.domain.Board.service.BoardService;

@RestController
@RequestMapping(value = "/api/v1/board")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

}
