package ac.kyungnam.book.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> applicationException(final BaseException e) {
        final ErrorCode code = e.getCode();

        log.warn("Application Exception Occurred -> {} | {} | {}",
                    code.getStatus(),
                    code.getCode(),
                    code.getMessage(),
                    e
        );

        return convert(code);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse> httpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.warn("HttpMessageNotReadableException Occurred -> {}", e.getMessage());
        return convert(GlobalErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<BaseResponse> unsatisfiedServletRequestParameterException(final UnsatisfiedServletRequestParameterException e) {
        log.warn("UnsatisfiedServletRequestParameterException Occurred -> {}", e.getMessage());
        return convert(GlobalErrorCode.VALIDATION_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> methodNotSupportedException(final MethodArgumentNotValidException e) {
        log.warn("HttpRequestMethodNotSupportedException Occurred -> {}", e.getMessage());
        List<FieldError> fieldErrors = e.getFieldErrors();
        return convert(GlobalErrorCode.VALIDATION_ERROR, extractErrorMessage(fieldErrors));
    }

    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<BaseResponse> noHandlerFoundException() {
        return convert(GlobalErrorCode.NOT_SUPPORTED_URI);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleAnyException() {
        return convert(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse> httpRequestMethodNotSupportedException() {
        return convert(GlobalErrorCode.NOT_SUPPORTED_METHOD);
    }


    private String extractErrorMessage(List<FieldError> fieldErrors) {
        if (fieldErrors.size() == 1) {
            return fieldErrors.get(0).getDefaultMessage();
        }

        StringBuffer buffer = new StringBuffer();
        for (FieldError error : fieldErrors) {
            buffer.append(error.getDefaultMessage()).append("/n");
        }

        return buffer.toString();
    }

    private ResponseEntity<BaseResponse> convert(ErrorCode code) {
        return ResponseEntity.status(code.getStatus())
                .body(BaseResponse.from(code));
    }

    private ResponseEntity<BaseResponse> convert(ErrorCode code, String message) {
        return ResponseEntity.status(code.getStatus())
                .body(BaseResponse.of(code, message));
    }
}
