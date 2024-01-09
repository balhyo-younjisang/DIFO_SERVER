package com.digitech.difo.domain.Board.controller;

import com.digitech.difo.global.common.SuccessResponse;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.domain.Board.domain.Board;


import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/board")
@AllArgsConstructor
public class BoardController {
    /**
     * 이정욱 시봉봉봉방방방놈아
     */
    private final BoardService boardService;
    @GetMapping("/{index}")
    public SuccessResponse<List<Board>> getEntriesByPage(@PathVariable int index, @PathParam(value = "count") int pageCount) {
        return boardService.getListsByPage(index, pageCount);
    }

    @GetMapping("/posts/{id}")
    public Board getPostById(@PathVariable Long id) {
        return boardService.getPostById(id);
    }

    @PostMapping("/register")
    public void createBoard(){
        return;
    }

    @PostMapping("/posts/{id}/like")
    public void addLikes(@PathVariable Long id, int likes){
        return;
    }
}
