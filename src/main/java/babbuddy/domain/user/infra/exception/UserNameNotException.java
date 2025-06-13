package babbuddy.domain.user.infra.exception;

import babbuddy.global.infra.exception.BabbuddyException;
import org.springframework.http.HttpStatus;

public class UserNameNotException extends BabbuddyException {
    public UserNameNotException() {
        super(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다.");
    }
}
