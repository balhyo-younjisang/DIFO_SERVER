package com.digitech.difo.domain.Member.controller;

import com.digitech.difo.domain.Member.service.MemberService;
import com.digitech.difo.global.auth.GoogleOAuthDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/member")
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * /api/v1/member/oauth/google/login 으로 GET 요청을 보낸 페이지를 구글 oauth 로그인 페이지로 리다이렉트
     * @param response Response 객체
     * @throws Exception
     */
    @GetMapping("/oauth/google")
    public void getGoogleAuthUrl(HttpServletResponse response) throws Exception {
        response.sendRedirect(memberService.getGoogleOAuthLoginRedirect());
    }

    @GetMapping("/oauth/google/redirect")
    public String callback(@RequestParam(name = "code") String code) throws Exception {
        return memberService.googleOAuthLogin(code).getEmail();
    }
    
}
