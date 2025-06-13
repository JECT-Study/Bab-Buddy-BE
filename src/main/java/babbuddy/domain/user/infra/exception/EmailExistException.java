package babbuddy.domain.user.infra.exception;

import babbuddy.global.infra.exception.BabbuddyException;
import org.springframework.http.HttpStatus;

public class EmailExistException extends BabbuddyException {

    public EmailExistException() {
        super(HttpStatus.CONFLICT, "이메일이 존재합니다.");
    }
}
