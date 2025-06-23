package babbuddy.global.infra.exception.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        int errorCode,
        String message,
        LocalDateTime time
) {
    public static ErrorResponse of(BabbuddyException exception) {
        return new ErrorResponse(
                exception.getHttpStatusCode(),
                exception.getErrorCode().getErrorCode(),
                exception.getMessage(),

                LocalDateTime.now()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpCode(),
                errorCode.getErrorCode(),
                errorCode.getMessage(),

                LocalDateTime.now()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(
                errorCode.getHttpCode(),
                errorCode.getErrorCode(),
                customMessage,

                LocalDateTime.now()
        );
    }
}