package com.digitech.difo.domain.Board.domain;

import com.digitech.difo.domain.BoardComment.domain.BoardComment;

import java.util.List;


public class BoardWithComments {
    private final Board board;
    private final List<BoardComment> comments;

    public BoardWithComments(Board board, List<BoardComment> comments) {
        this.board = board;
        this.comments = comments;
    }

    public Board getBoard() {
        return board;
    }

    public List<BoardComment> getComments() {
        return comments;
    }
}