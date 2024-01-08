package com.digitech.difo.domain.Member.controller;

import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Member.service.MemberService;
import com.digitech.difo.domain.Project.service.ProjectService;
import com.digitech.difo.global.common.SuccessResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ProjectService projectService;

    /**
     * /api/v1/member/oauth/google/login 으로 GET 요청을 보낸 페이지를 구글 oauth 로그인 페이지로 리다이렉트
     * @param response Response 객체
     * @throws Exception
     */
    @GetMapping("/oauth/google")
    public ResponseEntity<SuccessResponse<Void>> getGoogleAuthUrl(HttpServletResponse response) throws Exception {
        response.sendRedirect(this.memberService.getGoogleOAuthLoginRedirect());

        return null;
    }

    /**
     * 구글 OAuth 로그인 페이지에서 리다이렉트된 페이지에서 보낸 요청을 받아 Member 객체를 리턴
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/oauth/google/redirect")
    public ResponseEntity<SuccessResponse<MemberDTO.MemberResponseDTO>> callback(@RequestParam(name = "code") String code) throws Exception {
        SuccessResponse<MemberDTO.MemberResponseDTO> response = this.memberService.googleOAuthLogin(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    /**
     * 유저의 정보를 리턴
     * @param memberId
     * @return
     */
    @GetMapping("/details")
    public ResponseEntity<SuccessResponse<MemberDTO.MemberResponseDTO>> getMemberDetails(@RequestParam(name = "id") Long memberId) throws Exception {
        SuccessResponse<MemberDTO.MemberResponseDTO> response = this.memberService.findById(memberId);
        response.getData().setProjectsId(this.projectService.findByMemberId(response.getData().getMemberId()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
