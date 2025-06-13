package babbuddy.domain.oauth2.application.service;

import babbuddy.domain.oauth2.presentation.dto.response.LoginToken;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface KakaoLoginService {
    String login(String code, HttpServletResponse response) throws IOException;
}
