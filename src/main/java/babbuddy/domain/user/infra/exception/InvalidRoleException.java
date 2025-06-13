package babbuddy.domain.user.infra.exception;


import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import org.springframework.http.HttpStatus;

public class InvalidRoleException extends BabbuddyAuthException {
    public InvalidRoleException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
