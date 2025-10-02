package babbuddy.domain.oauth2.application.service.impl;

import babbuddy.domain.oauth2.application.service.LoginLinkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginLinkServiceImpl implements LoginLinkService {

    @Value("${oauth2.base-url}")
    private String baseUrl;

    @Value("${oauth2.client-id}")
    private String clientId;

    @Value("${oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public String getLoginLink(HttpServletRequest request) {
        // React가 어디서 요청했는지 확인
        String origin = request.getHeader("Origin");
        log.info(origin);
        String state = (origin != null && origin.contains("localhost")) ? "local" : "prod";

        log.info(state);

        return baseUrl +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=profile_nickname,profile_image,account_email" +
                "&state=" + state;
    }


}