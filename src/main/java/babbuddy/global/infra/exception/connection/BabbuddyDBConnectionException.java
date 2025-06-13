package babbuddy.global.infra.exception.connection;

import babbuddy.global.infra.exception.BabbuddyException;
import org.springframework.http.HttpStatus;

public class BabbuddyDBConnectionException extends BabbuddyException {
    public BabbuddyDBConnectionException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "DB Connection 과정 중 문제가 발생했습니다.");
    }
}
