package com.digitech.difo.domain.BoardComment.controller;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.service.BoardService;
import com.digitech.difo.domain.BoardComment.domain.BoardComment;
import com.digitech.difo.domain.BoardComment.service.BoardCommentService;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class BoardCommentControllerImpl implements BoardCommentController {
    private BoardCommentService boardCommentService;
    @Override
    public ResponseEntity<SuccessResponse<List<Board>>> getComments(long id) {
        return null;
    }

    @Override
    public void createComment(BoardComment boardComment, Optional<Long> memberId, long id) throws Exception {
        boardCommentService.createBoardComment(boardComment, memberId, id);
    }


}
