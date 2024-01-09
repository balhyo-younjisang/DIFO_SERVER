package com.digitech.difo.domain.Board.service;
import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Object[]> getListsByPage(int pageIndex) {
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return boardRepository.findListsByPage(pageable);
    }

    public Board getPostById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }
}
