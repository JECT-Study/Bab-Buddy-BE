package babbuddy.global.jwt.util.exception;


import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import org.springframework.http.HttpStatus;

public class BabbuddyJWTExpiredException extends BabbuddyAuthException {
    public BabbuddyJWTExpiredException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
