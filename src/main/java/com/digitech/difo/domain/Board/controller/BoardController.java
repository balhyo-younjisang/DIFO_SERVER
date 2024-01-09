package com.digitech.difo.domain.Board.controller;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.global.common.SuccessResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/v1/board")
public interface BoardController {
    /**
     * 이정욱 시봉봉봉방방방놈아
     */

    /**
     * 페이지의 인덱스와 찾을 갯수를 파라미터로 전달받아 검색 후 리턴
     * @param index
     * @param pageCount
     * @return
     */
    @GetMapping("/list")
    public SuccessResponse<List<Board>> getEntriesByPage(@PathParam(value = "list") int index, @PathParam(value = "count") int pageCount);

    /**
     * 게시글의 데이터를 전달받은 아이디로 검색 후 리턴
     * @param id
     * @return
     */
    @GetMapping("/posts/{id}")
    public Board getPostById(@PathVariable Long id);
}
