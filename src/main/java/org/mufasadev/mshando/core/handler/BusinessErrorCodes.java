package org.mufasadev.mshando.core.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {
    NO_CODE(0, "No Code", NOT_IMPLEMENTED),
    PASSWORD_MISMATCH(300, "Password mismatch.", BAD_REQUEST),
    INCORRECT_CURRENT_PASSWORD(301, "Please check your password", BAD_REQUEST),
    ACCOUNT_LOCKED(302, "User Account is Locked.", FORBIDDEN),
    ACCOUNT_DISABLED(303, "User Account is Disabled.", FORBIDDEN),
    BAD_CREDENTIALS(304, "Bad Credentials. Username or password incorrect.", UNAUTHORIZED),
    ;
    @Getter
    private final  int code;
    @Getter
    private final  String description;
    @Getter
    private final  HttpStatus httpStatus;

    BusinessErrorCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}