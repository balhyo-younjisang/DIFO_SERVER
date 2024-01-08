package com.digitech.difo.global.auth;

import lombok.*;

public class GoogleOAuthDto {
    @NoArgsConstructor
    @Data
    public static class GoogleOAuthTokenDTO {
        private String access_token;
        private Integer expires_in;
        private String scope;
        private String token_type;
        private String id_token;
    }

    // AccessToken을 활용해 JWT의 Payload 부분인 사용자 정보를 Response받는 VO
    @NoArgsConstructor
    @Data
    public static class GoogleUserInfoDTO {
        private String id;
        private String email;
        private String verified_email;
        private String name;
        private String picture;
        private String given_name;
        private String family_name;
        private String locale;
        private String hd;
    }
}
