package com.digitech.difo.domain.Board.controller;

import com.digitech.difo.global.common.SuccessResponse;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.domain.Board.domain.Board;


import java.util.List;

@RestController
@AllArgsConstructor
public class BoardControllerImpl implements BoardController {
    /**
     * 이정욱 시봉봉봉방방방놈아
     */
    private final BoardService boardService;

    @Override
    public SuccessResponse<List<Board>> getEntriesByPage(@PathVariable int index, @PathParam(value = "count") int pageCount) {
        return boardService.getListsByPage(index, pageCount);
    }

    @Override
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