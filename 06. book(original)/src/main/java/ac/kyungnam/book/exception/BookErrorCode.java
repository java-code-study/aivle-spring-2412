package ac.kyungnam.book.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookErrorCode implements ErrorCode {

    CANNOT_DELETE_BORROWED_BOOK(HttpStatus.FORBIDDEN, "CANNOT_DELETE_BORROWED_BOOK", "대출 중인 책은 삭제할 수 없습니다."),
    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "NOT_FOUND_BOOK", "존재하지 않는 책입니다");

    private final HttpStatus status;

    private final String code;

    private final String message;
}
