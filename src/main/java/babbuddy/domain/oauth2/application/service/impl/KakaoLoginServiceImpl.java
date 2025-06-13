package babbuddy.domain.oauth2.application.service.impl;


import babbuddy.domain.oauth2.application.service.*;
import babbuddy.domain.oauth2.presentation.dto.response.LoginToken;
import babbuddy.domain.oauth2.presentation.dto.response.oatuh.OAuth2TokenResponse;
import babbuddy.domain.oauth2.presentation.dto.response.oatuh.OAuth2UserResponse;
import babbuddy.domain.user.domain.entity.Role;
import babbuddy.global.jwt.domain.entity.KakaoJsonWebToken;
import babbuddy.global.jwt.domain.repository.KakaoJsonWebTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private final  KakaoAccessTokenAndRefreshTokenService KakaoAccessTokenAndRefreshTokenService;
    private final KakaoUserService KakaoUserService;
    private final CreateAccessTokenAndRefreshTokenService createAccessTokenAndRefreshTokenService;
    private final KakaoUserCreateService KakaoUserCreateService;
    private final KakaoJsonWebTokenRepository kakaoJsonWebTokenRepository;

    @Override
    public LoginToken login(String code, HttpServletResponse response){
        OAuth2TokenResponse oAuth2TokenResponse = KakaoAccessTokenAndRefreshTokenService.getAccessTokenAndRefreshToken(code);

        OAuth2UserResponse oAuth2UserResponse = KakaoUserService.getUser(oAuth2TokenResponse.accessToken());

        Map<String, String> values = KakaoUserCreateService.createKakaoUser(oAuth2TokenResponse,  oAuth2UserResponse);

        String userId = values.get("id");
        Role role = Role.valueOf(values.get("role"));
        String userEmail = values.get("email");

        // Kakao 토큰 저장
        KakaoJsonWebToken KakaoToken = KakaoJsonWebToken.builder()
                .userId(userId)
                .accessToken(oAuth2TokenResponse.accessToken())
                .refreshToken(oAuth2TokenResponse.refreshToken())
                .build();
        kakaoJsonWebTokenRepository.save(KakaoToken);

        Map<String, String> tokens = createAccessTokenAndRefreshTokenService.createAccessTokenAndRefreshToken(userId, role, userEmail);

        return LoginToken.of(tokens.get("access_token"), tokens.get("refresh_token_cookie"));
//        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.get("access_token"));
//        response.addHeader(HttpHeaders.SET_COOKIE, tokens.get("refresh_token_cookie"));

//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json");

//        response.getWriter().write("Successfully Login");

    }
}
