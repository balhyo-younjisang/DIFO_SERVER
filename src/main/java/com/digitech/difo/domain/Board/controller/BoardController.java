package com.digitech.difo.domain.Board.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.domain.Board.domain.Board;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/board")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/{index}")
    public List<Object[]> getEntriesByPage(@PathVariable int index) {
        return boardService.getListsByPage(index);
    }

    @GetMapping("/posts/{id}")
    public Board getPostById(@PathVariable Long id) {
        return boardService.getPostById(id);
    }

}
