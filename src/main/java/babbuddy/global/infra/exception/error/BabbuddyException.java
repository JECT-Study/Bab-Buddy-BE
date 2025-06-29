package babbuddy.global.infra.exception.error;


public class BabbuddyException extends RuntimeException {

    private final ErrorCode errorCode;

    public BabbuddyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BabbuddyException(ErrorCode errorCode, String detailMessage) {
        super(errorCode.getMessage() + " → " + detailMessage);
        this.errorCode = errorCode;
    }


    public BabbuddyException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpCode();
    }
}
