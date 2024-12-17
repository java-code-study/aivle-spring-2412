package ac.kyungnam.book.service;

import ac.kyungnam.book.domain.Book;
import ac.kyungnam.book.dto.BookDTO;
import ac.kyungnam.book.exception.BaseException;
import ac.kyungnam.book.mapper.BookControlMapper;
import ac.kyungnam.book.mapper.BookResponseMapper;
import ac.kyungnam.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookControlMapper controlMapper;

    @Mock
    private BookResponseMapper responseMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO.Post postDto;
    private BookDTO.Put putDto;
    private BookDTO.Patch patchDto;

    private Book borrowedBook;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test Title", "Test Sub Title", "Test Author", "Test Publisher", Book.Status.AVAILABLE);
        borrowedBook = new Book(2L, "Another Title", "Another Sub Title", "Another Author", "Another Publisher", Book.Status.BORROWED);
        postDto = new BookDTO.Post("Test Title", "Test Sub Title", "Test Author", "Test Publisher");
        putDto = new BookDTO.Put("Updated Title", "Updated Sub Title", "Updated Author", "Updated Publisher", Book.Status.AVAILABLE);
        patchDto = new BookDTO.Patch(Book.Status.BORROWED);
    }

    @Test
    @DisplayName("새로운 책 추가")
    void insertBook() {
        // Given
        when(controlMapper.PostDTOToEntity(postDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        // When
        Book savedBook = bookService.insertBook(postDto);

        // Then
        assertEquals(book, savedBook);
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("책 정보 업데이트")
    void updateBook() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        // When
        Book updateBook = bookService.updateBook(1L, putDto);

        // Then
        assertNotNull(updateBook);
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("책 상태 업데이트")
    void updateBookStatus() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        doAnswer(invocation -> {
            book.setStatus(Book.Status.BORROWED);
            return book;
        }).when(bookRepository).save(any(Book.class));

        // When
        Book updatedStatusBook = bookService.updateBookStatus(1L, patchDto);

        // Then
        assertEquals(Book.Status.BORROWED, updatedStatusBook.getStatus());
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("빌려진 책 삭제 시도")
    void deleteBorrowedBookThrowsException() {
        // Given
        when(bookRepository.findById(2L)).thenReturn(java.util.Optional.of(borrowedBook));

        // When + Then
        assertThrows(BaseException.class, () -> {
            bookService.deleteBook(2L);
        });

        verify(bookRepository, never()).delete(borrowedBook);
    }

    @Test
    @DisplayName("빌려지지 않은 책 삭제 시도")
    void deleteAvailableBook() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // When
        bookService.deleteBook(1L);

        // Then
        verify(bookRepository).delete(book);
    }

    @Test
    @DisplayName("책 정보 조회")
    void findBook() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(responseMapper.entityToResponse(book)).thenReturn(new BookDTO.Response());

        // When
        BookDTO.Response response = bookService.findBook(1L);

        // Then
        assertNotNull(response);
        verify(bookRepository).findById(1L);
    }

    @Test
    @DisplayName("책 리스트 조회")
    void findAllBooks() {
        // Given
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        when(responseMapper.booksToResponses(Arrays.asList(book))).thenReturn(Arrays.asList(new BookDTO.Response()));

        // When
        List<BookDTO.Response> responses = bookService.findBooks();

        // Then
        assertFalse(responses.isEmpty());
        verify(bookRepository).findAll();
    }
}