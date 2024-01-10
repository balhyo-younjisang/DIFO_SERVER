package com.digitech.difo.domain.BoardComment.domain;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
