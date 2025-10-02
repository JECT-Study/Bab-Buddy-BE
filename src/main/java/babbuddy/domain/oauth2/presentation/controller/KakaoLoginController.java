package babbuddy.domain.oauth2.presentation.controller;

import babbuddy.domain.oauth2.application.service.KakaoLoginService;
import babbuddy.domain.oauth2.presentation.dto.response.LoginToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/callback")
    public ResponseEntity<Void> login(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletResponse response) throws IOException {

        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, kakaoLoginService.login(code, response,state))
                .build();
    }


}
