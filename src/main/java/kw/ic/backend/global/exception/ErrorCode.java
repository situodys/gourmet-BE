package kw.ic.backend.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_LOGIN_EMAIL(BAD_REQUEST, "잘못된 이메일입니다."),
    INVALID_LOGIN_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),

    DUPLICATED_EMAIL(CONFLICT, "이미 회원가입된 이메일입니다."),

    ENTITY_NOT_FOUND(BAD_REQUEST, "존재하지 않는 엔티티입니다");

    private final HttpStatus httpStatus;
    private final String detail;
}
