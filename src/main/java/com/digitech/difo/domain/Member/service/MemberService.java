package com.digitech.difo.domain.Member.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import com.digitech.difo.global.auth.GoogleOAuthDto;
import com.digitech.difo.global.auth.GoogleOAuthService;
import com.digitech.difo.global.common.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class MemberService extends GoogleOAuthService {
    private final MemberRepository memberRepository;

    public MemberService(ObjectMapper objectMapper, RestTemplate restTemplate, MemberRepository memberRepository) {
        super(objectMapper, restTemplate);
        this.memberRepository = memberRepository;
    }

    public String getGoogleOAuthLoginRedirect() {
        return this.getGoogleOAuthRedirectUrl();
    }

    @Transactional
    public SuccessResponse<MemberDTO.MemberResponseDTO> googleOAuthLogin(String code) throws Exception {
        try {
            ResponseEntity<String> accessTokenResponse = this.requestAccessToken(code);
            GoogleOAuthDto.GoogleOAuthTokenDTO oAuthToken = this.getAccessToken(accessTokenResponse);
            ResponseEntity<String> userInfoResponse = this.requestUserInfo(oAuthToken);
            GoogleOAuthDto.GoogleUserInfoDTO googleUserInfoDto = this.getUserInfo(userInfoResponse);

            String[] emailAddress = googleUserInfoDto.getEmail().split("@");

            if(!emailAddress[1].equals("sdh.hs.kr")) throw new Exception("Google OAuth failed!");

            if(!memberRepository.existsByEmail(googleUserInfoDto.getEmail())) {
                memberRepository.save(Member.builder()
                        .email(googleUserInfoDto.getEmail())
                        .name(googleUserInfoDto.getName())
                        .picture(googleUserInfoDto.getPicture())
                        .build());

                Member createdMember =  memberRepository.findByEmail(googleUserInfoDto.getEmail());

                return new SuccessResponse<MemberDTO.MemberResponseDTO>(true,  createdMember.toDTO());
            }

            Member member = memberRepository.findByEmail(googleUserInfoDto.getEmail());
            return new SuccessResponse<MemberDTO.MemberResponseDTO>(true, member.toDTO());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public SuccessResponse<MemberDTO.MemberResponseDTO> findById(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isEmpty()) throw new NotFoundException("Member Not Found");

        return new SuccessResponse<MemberDTO.MemberResponseDTO>(true, member.get().toDTO());
    }
}
