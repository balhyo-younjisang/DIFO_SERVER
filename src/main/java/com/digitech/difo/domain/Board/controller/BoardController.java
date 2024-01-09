package com.digitech.difo.domain.Board.controller;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.global.common.SuccessResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/board")
public interface BoardController {

    /**
     * 페이지의 인덱스와 찾을 갯수를 파라미터로 전달받아 검색 후 리턴
     * @param index
     * @param pageCount
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<SuccessResponse<List<Board>>> getEntriesByPage( int index, int pageCount);

    /**
     * 게시글의 데이터를 전달받은 아이디로 검색 후 리턴
     * @param id
     * @return
     */
    @GetMapping("/posts/{id}")
    public Board getPostById(@PathVariable Long id);

    @PostMapping("/register")
    public Board createBoard(@RequestBody Board board) throws  Exception;
}