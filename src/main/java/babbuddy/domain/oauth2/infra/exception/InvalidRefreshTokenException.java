package babbuddy.domain.oauth2.infra.exception;

import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends BabbuddyAuthException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Refresh Token이 유효하지 않습니다.");
    }
}
