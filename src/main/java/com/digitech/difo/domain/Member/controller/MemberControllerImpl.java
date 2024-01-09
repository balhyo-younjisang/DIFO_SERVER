package com.digitech.difo.domain.Member.controller;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Member.service.MemberService;
import com.digitech.difo.domain.Project.service.ProjectService;
import com.digitech.difo.global.common.SuccessResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class MemberControllerImpl implements MemberController {
    private final MemberService memberService;
    private final ProjectService projectService;

    @Override
    public ResponseEntity<SuccessResponse<Void>> getGoogleAuthUrl(HttpServletResponse response) throws Exception {
        response.sendRedirect(this.memberService.getGoogleOAuthLoginRedirect());

        return null;
    }

    @Override
    public ResponseEntity<SuccessResponse<MemberDTO.MemberResponseDTO>> callback(@RequestParam(name = "code") String code) throws Exception {
        SuccessResponse<MemberDTO.MemberResponseDTO> response = this.memberService.googleOAuthLogin(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SuccessResponse<MemberDTO.MemberResponseDTO>> getMemberDetails(@RequestParam(name = "id") Long memberId) throws Exception {
        SuccessResponse<MemberDTO.MemberResponseDTO> response = this.memberService.findById(memberId);
        response.getData().setProjectsId(this.projectService.findByMemberId(response.getData().getMemberId()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse<Void>> updateMemberData(@ModelAttribute MemberDTO.MemberUpdateRequestDTO updateData) throws Exception {
        SuccessResponse<Void> response = this.memberService.findAndUpdateMemberById(updateData);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }
}
