package babbuddy.global.infra.exception;

import org.springframework.http.HttpStatus;

public class BabbuddyException extends RuntimeException {

    private final HttpStatus status;

    public BabbuddyException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
