package ac.kyungnam.book.service;

import ac.kyungnam.book.domain.Book;
import ac.kyungnam.book.dto.BookDTO;
import ac.kyungnam.book.exception.BaseException;
import ac.kyungnam.book.exception.BookErrorCode;
import ac.kyungnam.book.mapper.BookControlMapper;
import ac.kyungnam.book.mapper.BookResponseMapper;
import ac.kyungnam.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookControlMapper controlMapper;
    private final BookResponseMapper responseMapper;

    public Book insertBook(BookDTO.Post dto) {
        Book book = controlMapper.PostDTOToEntity(dto);
        return saveBook(book);
    }

    public Book updateBook(Long id, BookDTO.Put dto) {
        Book book = findVerifiredBook(id);
        controlMapper.PutDTOToEntity(dto, book);
        return saveBook(book);
    }

    public Book updateBookStatus(Long id, BookDTO.Patch dto) {
        Book book = findVerifiredBook(id);
        controlMapper.PatchDTOToEntity(dto, book);
        return saveBook(book);
    }

    public Book updateBookTitleAndPublisher(Long id, BookDTO.Rollback dto) {
        Book book = findVerifiredBook(id);

        updateBookTitle(book, dto.getTitle());
        updateBookPublisher(book, dto.getPublisher());

        return book;
    }

    public void updateBookTitle(Book book, String title) {
        book.setTitle(title);
        saveBook(book);
    }

    public void updateBookPublisher(Book book, String publisher) {
        book.setPublisher(publisher);
        if(true) {
            throw new RuntimeException("트랜잭션이 롤백되지 않습니다.");
        }
        saveBook(book);
    }

    public void deleteBook(Long id) {
        Book book = findVerifiredBook(id);
        if(book.getStatus() == Book.Status.BORROWED) {
            throw BaseException.type(BookErrorCode.CANNOT_DELETE_BORROWED_BOOK);
        }
        bookRepository.delete(book);
    }

    public BookDTO.Response findBook(Long id) {
        Book book = findVerifiredBook(id);
        return responseMapper.entityToResponse(book);
    }

    public List<BookDTO.Response> findBooks() {
        List<Book> books = bookRepository.findAll();
        return responseMapper.booksToResponses(books);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findVerifiredBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> BaseException.type(BookErrorCode.NOT_FOUND_BOOK));
    }
}
