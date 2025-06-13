package babbuddy.domain.user.infra.exception;

import babbuddy.global.infra.exception.BabbuddyException;
import org.springframework.http.HttpStatus;


public class UserNameExistException extends BabbuddyException {

    public UserNameExistException() {
        super(HttpStatus.CONFLICT, "아이디가 이미 존재합니다.");
    }
}
