package com.digitech.difo.domain.Board.controller;

import com.digitech.difo.global.common.SuccessResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import com.digitech.difo.domain.Board.domain.Board;


import java.util.List;

@RequestMapping(value = "/api/v1/board")
public interface BoardController {
    /**
     *
     * @param index
     * @param pageCount
     * @return
     * @throws Exception
     */
    @GetMapping("/{index}")
    public SuccessResponse<List<Board>> getEntriesByPage(@PathVariable int index, Integer pageCount) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/posts/{id}")
    public Board getPostById(@PathVariable Long id) throws Exception;

    /**
     *
     * @param board
     * @throws Exception
     */
    @PostMapping("/register")
    public void createBoard(@RequestBody Board board) throws  Exception;

    /**
     *
     * @param id
     * @param likes
     * @throws Exception
     */
    @PostMapping("/posts/{id}/like")
    public void addLikes(@PathVariable Long id, int likes) throws  Exception;
}
