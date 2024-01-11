package com.digitech.difo.domain.Board.service;
import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.domain.BoardWithComments;
import com.digitech.difo.domain.Board.repository.BoardRepository;
import com.digitech.difo.domain.BoardComment.domain.BoardComment;
import com.digitech.difo.domain.BoardComment.repository.BoardCommentRepository;
import com.digitech.difo.domain.BoardComment.service.BoardCommentService;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;

    /**
     * Page Sort하기 이 씨봉방
     * @param pageIndex
     * @return
     */
    @Transactional
    public SuccessResponse<List<Board>> getListsByPage(int pageIndex) {
        int boardCount = 3;
        Pageable pageable = PageRequest.of(pageIndex, boardCount);

        Comparator<Board> dateComparator = Comparator.comparing(Board::getBoardId).reversed();
        List<Board> sortedBoards = new ArrayList<>(boardRepository.findAll(pageable).getContent());
        Collections.sort(sortedBoards, dateComparator);

        return new SuccessResponse<>(true, sortedBoards);
    }

    @Transactional
    public SuccessResponse<BoardWithComments> getPostByIdWithComments(Long id) {
        Board board = boardRepository.findById(id)
                .orElse(null);

        if (board == null) {
            return new SuccessResponse<>(false, null); // 게시물을 찾을 수 없는 경우 처리
        }

        List<BoardComment> comments = boardCommentRepository.findAllCommentsByBoardBoardId(id);
        BoardWithComments boardWithComments = new BoardWithComments(board, comments);

        return new SuccessResponse<>(true, boardWithComments);
    }

    public Board createPost(Board board) { return boardRepository.save(board);}

    public void deletePost(Board board) { boardRepository.delete(board);}
    @Transactional
    public SuccessResponse<Void> deletePost(Long id) throws Exception {
        try {
            Optional<Board> existsProject = this.boardRepository.findById(id);

            if(existsProject.isEmpty()) {
                throw new NotFoundException("Board is Not found");
            }

            this.boardRepository.deleteById(id);
            return new SuccessResponse<Void>(true, null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public  Board updateLikes(Long id) throws  Exception{
        try {
            Board board = boardRepository.findByBoardId(id);
            board.setLikes(board.getLikes() + 1);
            return boardRepository.save(board);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public ResponseEntity<SuccessResponse<List<Board>>> sortBoard(String type) throws Exception {
        try {
            List<Board> sortedBoard;
            if (type.equals("recommend")){
                sortedBoard = boardRepository.findByOrderByLikesDesc();
            } else {
                sortedBoard = boardRepository.findByOrderByTimeDesc();
            }
            return ResponseEntity.ok(new SuccessResponse<>(true, sortedBoard));
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
