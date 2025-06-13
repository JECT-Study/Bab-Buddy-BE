package babbuddy.global.infra.exception.auth;

import babbuddy.global.infra.exception.BabbuddyException;
import org.springframework.http.HttpStatus;

public class BabbuddyAuthException extends BabbuddyException {
    public BabbuddyAuthException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}