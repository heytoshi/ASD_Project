package edu.miu.cs489.tsogt.backend.service;

import edu.miu.cs489.tsogt.backend.dto.book.BookRequest;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Page<BookResponce> getAllBooks(Pageable pageable);
    List<BookResponce2> getAllBooksByOwner(String username);
    BookResponce getBookById(int id);
    List<BookResponce> searchBooks(String searchString);
    BookResponce2 addBook(BookRequest book);
    BookResponce2 updateBook(BookRequest book, int id);
    BookResponce2 deleteBook(int id);
}
