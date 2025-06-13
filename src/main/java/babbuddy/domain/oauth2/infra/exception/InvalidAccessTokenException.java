package babbuddy.domain.oauth2.infra.exception;

import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends BabbuddyAuthException {
    public InvalidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Access Token이 유효하지 않습니다.");
    }
}
