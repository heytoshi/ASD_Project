package edu.miu.cs489.tsogt.backend.repository;

import edu.miu.cs489.tsogt.backend.dto.book.BookResponce;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce2;
import edu.miu.cs489.tsogt.backend.enums.BookStatus;
import edu.miu.cs489.tsogt.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Integer> {
    @Query(value = "select b from Book b where b.owner.username = :username")
    List<Book> findBookByUsername(String username);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCaseOrPublisherContainingIgnoreCase(
            String title,
            String author,
            String genre,
            String publisher
    );
}
