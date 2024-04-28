package edu.miu.cs489.tsogt.backend.service.impl;

import edu.miu.cs489.tsogt.backend.dto.book.BookRequest;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce2;
import edu.miu.cs489.tsogt.backend.dto.user.UserResponce;
import edu.miu.cs489.tsogt.backend.enums.BookStatus;
import edu.miu.cs489.tsogt.backend.model.Book;
import edu.miu.cs489.tsogt.backend.model.User;
import edu.miu.cs489.tsogt.backend.repository.BookRepo;
import edu.miu.cs489.tsogt.backend.repository.UserRepo;
import edu.miu.cs489.tsogt.backend.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final UserRepo userRepo;

    public BookServiceImpl(BookRepo bookRepo, UserRepo userRepo) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Page<BookResponce> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepo.findAll(pageable);
        return mapBookPageToBookResponsePage(books);
    }

    @Override
    public List<BookResponce2> getAllBooksByOwner(String username) {
        List<Book> books = bookRepo.findBookByUsername(username);
        return mapBooksToBookResponse2List(books);
    }

    @Override
    public BookResponce getBookById(int id) {
        Book book = bookRepo.findById(id).orElse(null);
        return mapBookToBookResponse(book);
    }

    @Override
    public List<BookResponce> searchBooks(String searchString) {
        List<Book> books = bookRepo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCaseOrPublisherContainingIgnoreCase(searchString, searchString, searchString, searchString);
        return mapBooksToBookResponseList(books);
    }

    @Override
    public BookResponce2 addBook(BookRequest book) {
        Optional<User> userOptional = userRepo.findByUsername(book.username());
        User user = userOptional.get();
        Book newBook = new Book(
                book.title(),
                book.author(),
                book.genre(),
                book.publisher(),
                book.description(),
                book.year(),
                BookStatus.AVAILABLE,
                user
        );
        bookRepo.save(newBook);
        return mapBookToBookResponse2(newBook);
    }

    @Override
    public BookResponce2 updateBook(BookRequest book, int id) {
        var bookFind = bookRepo.findById(id).orElse(null);
        bookFind.setTitle(book.title());
        bookFind.setAuthor(book.author());
        bookFind.setGenre(book.genre());
        bookFind.setPublisher(book.publisher());
        bookFind.setDescription(book.description());
        bookFind.setYear(book.year());
        Book bookUpdated = bookRepo.save(bookFind);
        return mapBookToBookResponse2(bookUpdated);
    }

    @Override
    public BookResponce2 deleteBook(int id) {
        var book = bookRepo.findById(id).orElse(null);
        bookRepo.delete(book);
        return mapBookToBookResponse2(book);
    }

    private Page<BookResponce> mapBookPageToBookResponsePage(Page<Book> bookPage) {
        List<BookResponce> bookResponses = bookPage.getContent().stream()
                .map(this::mapBookToBookResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponses, bookPage.getPageable(), bookPage.getTotalElements());
    }

    private List<BookResponce> mapBooksToBookResponseList(List<Book> books) {
        return books.stream()
                .map(this::mapBookToBookResponse)
                .collect(Collectors.toList());
    }

    private List<BookResponce2> mapBooksToBookResponse2List(List<Book> books) {
        return books.stream()
                .map(this::mapBookToBookResponse2)
                .collect(Collectors.toList());
    }

    private BookResponce mapBookToBookResponse(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponce(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublisher(),
                book.getDescription(),
                book.getYear(),
                book.getStatus(),
                mapUserToUserResponse(book.getOwner())
        );
    }

    private BookResponce2 mapBookToBookResponse2(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponce2(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublisher(),
                book.getDescription(),
                book.getYear(),
                book.getStatus()
        );
    }

    private UserResponce mapUserToUserResponse(User user) {
        return new UserResponce(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUsername()
        );
    }
}
