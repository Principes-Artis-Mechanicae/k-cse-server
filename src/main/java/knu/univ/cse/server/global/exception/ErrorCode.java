package knu.univ.cse.server.global.exception;

public record ErrorCode(String code, String message) {
    public static ErrorCode of(String code, String message) {
        return new ErrorCode(code, message);
    }
}
