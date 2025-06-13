package babbuddy.global.jwt.util.exception;

import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import org.springframework.http.HttpStatus;

public class BabbuddyJWTException extends BabbuddyAuthException {
    public BabbuddyJWTException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
