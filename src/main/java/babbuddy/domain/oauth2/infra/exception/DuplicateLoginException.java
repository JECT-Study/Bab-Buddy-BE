package babbuddy.domain.oauth2.infra.exception;


import babbuddy.global.infra.exception.auth.BabbuddyAuthException;
import org.springframework.http.HttpStatus;

public class DuplicateLoginException extends BabbuddyAuthException {
    public DuplicateLoginException() {
        super(HttpStatus.UNAUTHORIZED, "중복 로그인은 허용되지 않습니다.");
    }
}
