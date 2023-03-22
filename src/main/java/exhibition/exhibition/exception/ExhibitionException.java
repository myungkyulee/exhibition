package exhibition.exhibition.exception;

import lombok.Getter;

@Getter
public class ExhibitionException extends RuntimeException {
    private ErrorCode errorCode;

    public ExhibitionException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
