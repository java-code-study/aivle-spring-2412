package ac.kyungnam.book.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalErrorCode implements ErrorCode {

    /* 성공 응답 관련 에러 코드 */
    SUCCESS(HttpStatus.OK, "SUCCESS", "요청에 성공했습니다"),
    CREATED(HttpStatus.CREATED, "CREATED", "요청에 성공했으며 리소스가 정상적으로 생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "ACCEPTED", "요청에 성공했으나 처리가 완료되지 않았습니다"),

    /* 클라이언트 요청 오류 관련 에러 코드 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "클라이언트의 요청이 잘못 되었습니다"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "요청 파라미터가 유효하지 않습니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "인증되지 않은 사용자입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "접근이 금지되었습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "요청하신 리소스를 찾을 수 없습니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "허용되지 않은 요청 메소드입니다."),
    NOT_SUPPORTED_URI(HttpStatus.NOT_FOUND, "NOT_SUPPORTED_URI", "지원되지 않는 URI입니다"),
    NOT_SUPPORTED_METHOD(HttpStatus.NOT_FOUND, "NOT_FOUND", "지원되지 않는 메소드입니다"),
    NOT_SUPPORTED_MEDIA_TYPE(HttpStatus.NOT_FOUND, "NOT_SUPPORTED_MEDIA_TYPE", "지원되지 않는 미디어 타입입니다"),

    /* 서버 오류 관련 에러코드 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버에 오류가 발생했습니다"),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "BAD_GATEWAY", "게이트웨이에 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", "서비스를 이용할 수 없습니다"),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "GATEWAY_TIMEOUT", "게이트웨이가 응답하지 않습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;

    GlobalErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
