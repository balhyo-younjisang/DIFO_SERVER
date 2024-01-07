package com.digitech.difo.domain.Member.service;

import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import com.digitech.difo.global.auth.GoogleOAuthDto;
import com.digitech.difo.global.auth.GoogleOAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

    public Member googleOAuthLogin(String code) throws Exception {
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

                return memberRepository.findByEmail(googleUserInfoDto.getEmail());
            }

            return memberRepository.findByEmail(googleUserInfoDto.getEmail());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
