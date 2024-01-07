package com.digitech.difo.domain.Board.repository;
import com.digitech.difo.domain.Board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BoardRepository extends JpaRepository<Board, Long> {
}
