package edu.miu.cs489.tsogt.backend.controller;

import edu.miu.cs489.tsogt.backend.dto.book.BookRequest;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce2;
import edu.miu.cs489.tsogt.backend.service.BookService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/project/api/v1/service/search/{searchString}")
    public ResponseEntity<List<BookResponce>> searchBooks(@PathVariable(required = false) String searchString) {
        List<BookResponce> books = bookService.searchBooks(searchString);
        return ResponseEntity.ok(books);
    }
    @GetMapping(value = "/project/api/v1/service/book")
    public ResponseEntity<List<BookResponce>> getBooks(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookResponce> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books.getContent());
    }

    @GetMapping(value = "/project/api/v1/service/book/{username}")
    public ResponseEntity<List<BookResponce2>> searchBookById(@PathVariable String username) {
        List<BookResponce2> books = bookService.getAllBooksByOwner(username);
        return ResponseEntity.ok(books);
    }

    @PostMapping(value = "/project/api/v1/service/book/")
    public ResponseEntity<BookResponce2> addBook(@RequestBody BookRequest bookRequest) {
        BookResponce2 book = bookService.addBook(bookRequest);
        return ResponseEntity.ok(book);
    }

    @PutMapping(value = "/project/api/v1/service/book/{id}")
    public ResponseEntity<BookResponce2> updateBook(@RequestBody BookRequest bookRequest, @PathVariable int id) {
        BookResponce2 book = bookService.updateBook(bookRequest, id);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping(value = "/project/api/v1/service/book/{id}")
    public ResponseEntity<BookResponce2> updateBook(@PathVariable int id) {
        BookResponce2 book = bookService.deleteBook(id);
        return ResponseEntity.ok(book);
    }

}
