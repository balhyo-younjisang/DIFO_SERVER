package com.digitech.difo.domain.Board.service;
import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.repository.BoardRepository;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * Page Sort하기 이 씨봉방
     * @param pageIndex
     * @return
     */
    @Transactional
    public SuccessResponse<List<Board>> getListsByPage(int pageIndex, int boardCount) {
        Pageable pageable = PageRequest.of(pageIndex, boardCount);
        return new SuccessResponse<>(true, boardRepository.findAll(pageable).getContent());
    }

    public Board getPostById(Long id) {
        return boardRepository.findById(id).orElse(null);
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
}
