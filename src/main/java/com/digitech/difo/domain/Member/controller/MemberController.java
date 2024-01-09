package com.digitech.difo.domain.Member.controller;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.global.common.SuccessResponse;
import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "/api/v1/member")
public interface MemberController {

    /**
     * /api/v1/member/oauth/google/login 으로 GET 요청을 보낸 페이지를 구글 oauth 로그인 페이지로 리다이렉트
     * @param response Response 객체
     * @throws Exception
     */
    @GetMapping("/oauth/google")
    public ResponseEntity<SuccessResponse<Void>> getGoogleAuthUrl(HttpServletResponse response) throws Exception;

    /**
     * 구글 OAuth 로그인 페이지에서 리다이렉트된 페이지에서 보낸 요청을 받아 Member 객체를 리턴
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/oauth/google/redirect")
    public ResponseEntity<SuccessResponse<MemberDTO.MemberResponseDTO>> callback(@RequestParam(name = "code") String code) throws Exception;

    /**
     * Member Id를 Member Table 에서 검색해 유저 정보를 리턴
     * @param memberId
     * @return
     */
    @GetMapping("/details")
    public ResponseEntity<SuccessResponse<MemberDTO.MemberResponseDTO>> getMemberDetails(@RequestParam(name = "id") Long memberId) throws Exception;

    /**
     * Member Id를 Parameter 로 받은 후 Id에 맞는 Member Data를 수정
     */
    @PatchMapping("/update")
    public ResponseEntity<SuccessResponse<Void>> updateMemberData(@ModelAttribute MemberDTO.MemberUpdateRequestDTO memberUpdateRequestDTO) throws Exception;

}
