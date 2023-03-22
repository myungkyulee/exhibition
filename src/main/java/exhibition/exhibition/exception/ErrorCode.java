package exhibition.exhibition.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통적인 에러코드
    ACCESS_DENIED(401, "C001", "접근할 수 있는 권한이 없습니다."),
    INVALID_TOKEN(401, "C002", "유효하지 않은 토큰입니다."),

    // visitor 에러코드
    NOT_FOUND_VISITOR(400, "V001", "visitor를 찾을 수 없습니다."),
    PASSWORD_UN_MATCH(400, "V002", "비밀번호가 일치하지 않습니다.");


    private final int status;
    private final String code;
    private final String message;

}
