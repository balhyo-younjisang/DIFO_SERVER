package com.digitech.difo.domain.BoardComment.controller;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.BoardComment.domain.BoardComment;
import com.digitech.difo.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/api/v1/board")
public interface BoardCommentController {
    @GetMapping("/posts/{id}/comment")
    public ResponseEntity<SuccessResponse<List<Board>>> getComments(@PathVariable long id) throws Exception;

    @PostMapping("/posts/{id}/comment")
    public void createComment(@RequestBody BoardComment boardComment, Optional<Long>  memberId, @PathVariable long id) throws  Exception;

}
