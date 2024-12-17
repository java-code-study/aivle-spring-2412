package ac.kyungnam.book.controller;

import ac.kyungnam.book.domain.Book;
import ac.kyungnam.book.dto.BookDTO;
import ac.kyungnam.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    public final BookService bookService;

    @GetMapping
    public List<BookDTO.Response> getBooks() {
        return bookService.findBooks();
    }

    @GetMapping("/{bookId}")
    public BookDTO.Response getBook(@PathVariable("bookId") Long id) {
        return bookService.findBook(id);
    }

    @PostMapping
    @Operation(summary = "책 생성", description = "책을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<Book> insertBook(@Valid @RequestBody BookDTO.Post dto) {
        Book savedBook = bookService.insertBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "책 정보 수정", description = "책의 정보를 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "책이 존재하지 않음", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters(@Parameter(name = "bookId", description = "책 ID", required = true))
    public Book updateBook(@PathVariable("bookId") Long id, @Valid @RequestBody BookDTO.Put dto) {
        return bookService.updateBook(id, dto);
    }

    @PatchMapping("/{bookId}")
    public Book updateBookStatus(@PathVariable("bookId") Long id, @Valid @RequestBody BookDTO.Patch dto) {
        return bookService.updateBookStatus(id, dto);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long id) {
        bookService.deleteBook(id);
    }
}
