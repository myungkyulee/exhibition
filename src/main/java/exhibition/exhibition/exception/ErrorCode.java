package exhibition.exhibition.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_VISITOR(HttpStatus.NOT_FOUND, "visitor를 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "접근할 수 있는 권한이 없습니다."),
    PASSWORD_UN_MATCH(HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
