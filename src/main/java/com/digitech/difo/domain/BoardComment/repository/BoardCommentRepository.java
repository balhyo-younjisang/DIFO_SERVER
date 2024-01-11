package com.digitech.difo.domain.BoardComment.repository;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.BoardComment.domain.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findAllCommentsByBoardBoardId(long boardId);
}
