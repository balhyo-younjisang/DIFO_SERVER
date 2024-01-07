package com.digitech.difo.domain.Member.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.digitech.difo.domain.Member.domain.Member;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("Member가 시작되는지 확인하는 테스트")
    public void signUpTest() {
        // given
        String email = "sdh230308@sdh.hs.kr";
        String name = "윤지상";
        String password = "123456789";

        memberRepository.save(Member.builder().email(email).name(name).password(password).build());
    }
}
