package exhibition.exhibition.exception;

import lombok.Getter;

@Getter
public class ExhibitionException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public ExhibitionException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    @Override
    public String toString() {
        return message;
    }
}
