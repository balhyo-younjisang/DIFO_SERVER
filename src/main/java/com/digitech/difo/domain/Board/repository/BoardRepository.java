package com.digitech.difo.domain.Board.repository;
import com.digitech.difo.domain.Board.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<String> findTitlesBy(Pageable pageable);
}
