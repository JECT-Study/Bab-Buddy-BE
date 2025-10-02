package babbuddy.domain.oauth2.presentation.controller;

import babbuddy.domain.oauth2.application.service.LoginLinkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class LoginLinkController {

    private final LoginLinkService loginLinkService;

    @GetMapping("/login")
    public ResponseEntity<Void> loginPage(HttpServletRequest request) {
        // 로그인 링크 생성 시 state를 포함
        String loginLink = loginLinkService.getLoginLink(request);

        return ResponseEntity
                .status(HttpStatus.FOUND)   // 302 Redirect
                .header("Location", loginLink)
                .build();
    }


}
