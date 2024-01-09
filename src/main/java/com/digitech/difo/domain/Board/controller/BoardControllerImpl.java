package com.digitech.difo.domain.Board.controller;

import com.digitech.difo.global.common.SuccessResponse;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.domain.Board.domain.Board;


import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@AllArgsConstructor
public class BoardControllerImpl implements BoardController {
    /**
     * 이정욱 시봉봉봉방방방놈아
     */
    private final BoardService boardService;

    @Override
    public ResponseEntity<SuccessResponse<List<Board>>> getEntriesByPage(@PathVariable int index, @PathParam(value = "count") int pageCount) {
        SuccessResponse<List<Board>> boards = boardService.getListsByPage(index, pageCount);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(boards, headers, HttpStatus.OK);
    }

    @Override
    public Board getPostById(@PathVariable Long id) {
        return boardService.getPostById(id);
    }

    @Override
    public Board createBoard(Board board) throws Exception {
        return boardService.createPost(board);
    }


}