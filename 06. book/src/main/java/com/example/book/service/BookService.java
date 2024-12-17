package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    //책을 등록
    public Book insertBook(Book book) {
        return bookRepository.save(book);
    }
    //책을 업데이트(PUT)
    public Book updateBook(Long id, Book book) {
        Book b = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));
        b.setTitle(book.getTitle());
        b.setSubTitle(book.getSubTitle());
        b.setAuthor(book.getAuthor());
        b.setPublisher(book.getPublisher());
        b.setStatus(book.getStatus());
        return bookRepository.save(b);
    }
    //책을 업데이트(PATCH)
    public Book updateBook(Long id, Book.Status status) {
        Book b = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));
        b.setStatus(status);
        return bookRepository.save(b);
    }

    //책을 삭제하고
    //책을 조회하고
}
