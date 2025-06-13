package babbuddy.domain.oauth2.infra.exception;

import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotExistException extends BabbuddyAuthException {
    public RefreshTokenNotExistException() {
        super(HttpStatus.UNAUTHORIZED, "Refresh Token이 DB에 존재하지 않습니다.");
    }
}
