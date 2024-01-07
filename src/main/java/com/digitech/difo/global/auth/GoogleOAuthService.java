package com.digitech.difo.global.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class GoogleOAuthService {
    private final String GOOGLE_LOGIN_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${oauth.google.client-id}")
    private String googleClientId;

    @Value("${oauth.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth.google.redirect}")
    private String googleRedirectUrl;

    @Value("${oauth.google.scope}")
    private String scope;

    public String getGoogleOAuthRedirectUrl() {
        Map<String, Object> params = new HashMap<>();

        params.put("client_id", this.googleClientId);
        params.put("redirect_uri", this.googleRedirectUrl);
        params.put("response_type", "code");
        params.put("scope", getScopeUrl(this.scope));

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return this.GOOGLE_LOGIN_URL + "?" + paramStr;
    }

    //  scope의 값을 보내기 위해 띄어쓰기 값을 UTF-8로 변환하는 로직 포함
    private String getScopeUrl(String scope) { return this.scope.replaceAll(",", "%20");
    }

    protected ResponseEntity<String> requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();

        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        if(responseEntity.getStatusCode() != HttpStatus.OK) return null;

        return responseEntity;
    }

    protected GoogleOAuthDto.GoogleOAuthTokenDTO getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        return objectMapper.readValue(response.getBody(), GoogleOAuthDto.GoogleOAuthTokenDTO.class);
    }

    protected ResponseEntity<String> requestUserInfo(GoogleOAuthDto.GoogleOAuthTokenDTO googleOAuthTokenDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + googleOAuthTokenDTO.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);

        return response;
    }

    protected GoogleOAuthDto.GoogleUserInfoDTO getUserInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleOAuthDto.GoogleUserInfoDTO googleUserInfoDTO = objectMapper.readValue(response.getBody(), GoogleOAuthDto.GoogleUserInfoDTO.class);

        return googleUserInfoDTO;
    }
}
