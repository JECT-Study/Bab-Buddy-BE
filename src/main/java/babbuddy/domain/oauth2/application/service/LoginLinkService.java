package babbuddy.domain.oauth2.application.service;

import jakarta.servlet.http.HttpServletRequest;

public interface LoginLinkService {
    String getLoginLink(HttpServletRequest request);
}
