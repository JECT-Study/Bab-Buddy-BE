package babbuddy.global.infra.exception;



import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import babbuddy.global.infra.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BabbuddyException.class)
    public ResponseEntity<ErrorResponse> handleFlowException(BabbuddyException e) {
        log.error("FlowException caught - ErrorCode: {}, Message: {}",
                e.getErrorCode().getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatusCode())
                .body(ErrorResponse.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        log.error("Unexpected exception caught: ", e);
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.of(ErrorCode.SERVER_UNTRACKED_ERROR));
    }
}
