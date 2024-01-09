package com.digitech.difo.domain.Board.controller;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardControllerImpl implements BoardController{
    private final BoardService boardService;

    @Override
    public SuccessResponse<List<Board>> getEntriesByPage(int index, Integer pageCount) throws Exception {
        pageCount = 10;
        return boardService.getListsByPage(index, pageCount);
    }

    @Override
    public Board getPostById(Long id) throws Exception {
        return boardService.getPostById(id);
    }

    @Override
    public void createBoard(Board board) throws Exception {

    }

    @Override
    public void addLikes(Long id, int likes) throws Exception {

    }
}
