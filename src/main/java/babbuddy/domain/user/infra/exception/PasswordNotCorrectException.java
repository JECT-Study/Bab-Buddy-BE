package babbuddy.domain.user.infra.exception;

import babbuddy.global.infra.exception.BabbuddyException;
import org.springframework.http.HttpStatus;

public class PasswordNotCorrectException extends BabbuddyException {
    public PasswordNotCorrectException() {
        super(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }
}
