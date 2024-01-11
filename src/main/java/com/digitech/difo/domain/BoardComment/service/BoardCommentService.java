package com.digitech.difo.domain.BoardComment.service;

import com.digitech.difo.domain.Board.domain.Board;
import com.digitech.difo.domain.Board.domain.BoardWithComments;
import com.digitech.difo.domain.Board.repository.BoardRepository;
import com.digitech.difo.domain.BoardComment.domain.BoardComment;
import com.digitech.difo.domain.BoardComment.repository.BoardCommentRepository;
import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createBoardComment(BoardComment boardComment,  long boardId, Optional<Long> memberId) throws Exception {
        System.out.println(memberId);
        // 게시물이 존재하는지 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new Exception("ID " + boardId + "에 해당하는 게시물을 찾을 수 없습니다."));

        memberId.ifPresent(id -> {
            if (!memberRepository.existsById(id)) {
                try {
                    throw new Exception("ID " + id + "에 해당하는 회원을 찾을 수 없습니다.");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("ID " + id + "에 해당하는 회원을 찾을 수 없습니다."));
            boardComment.setMember(member);
        });
        boardComment.setBoard(board);
        boardCommentRepository.save(boardComment);
    }
}