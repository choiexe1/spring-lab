package devjay.springlab.domain.exception;

public abstract class CustomException extends RuntimeException {
    protected CustomException() {
    }

    protected CustomException(String message) {
        super(message);
    }

    protected CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CustomException(Throwable cause) {
        super(cause);
    }

    protected CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    // 자식 클래스에서 구현하도록 강제하는 추상 메서드
    protected abstract String defineErrorCode();

    public String getErrorCode() {
        return defineErrorCode();
    }
}
