package com.example.book.controller;

import com.example.book.domain.Book;
import com.example.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    public final BookService bookService;

    //GET 다건
    @GetMapping
    public List<Book> getBooks(){
        return bookService.findBooks();
    }

    //GET 단건
    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable("bookId") Long bookId) {
        return bookService.findBook(bookId);
    }

    //POST
    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookService.insertBook(book);
    }
    //PUT
    @PutMapping("/{bookId}")
    public Book updateBook(
            @PathVariable("bookId") Long id, @RequestBody Book book){
        return bookService.updateBook(id, book);
    }
    //PATCH
    @PatchMapping("/{bookId}")
    public Book updateBookStatus(
            @PathVariable("bookId") Long id, @RequestBody Book book){
        return bookService.updateBook(id, book.getStatus());
    }
    //DELETE
    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long id) {
        bookService.deleteBook(id);
    }
}
