package com.digitech.difo.domain.BoardComment.controller;

import com.digitech.difo.domain.Board.domain.Board;
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
    public ResponseEntity<SuccessResponse<List<BoardComment>>> getComments(long id) throws Exception {
        return null;
    }

    @Override
    public void createComment(BoardComment boardComment, long id, Optional<Long> memberId) throws Exception {
        boardCommentService.createBoardComment(boardComment, id, memberId);
    }


}
