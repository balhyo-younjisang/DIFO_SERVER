package com.digitech.difo.domain.BoardComment.service;

import com.digitech.difo.domain.BoardComment.domain.BoardComment;
import com.digitech.difo.domain.BoardComment.repository.BoardCommentRepository;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createBoardComment(BoardComment boardComment, Optional<Long> memberId, long Id){
        long memberIdValue = memberId.orElse(0L);
        System.out.println(memberRepository.findById(memberIdValue));
        memberRepository.findById(memberIdValue);
    }
}
