package edu.miu.cs489.tsogt.backend;

import edu.miu.cs489.tsogt.backend.controller.BookController;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce2;
import edu.miu.cs489.tsogt.backend.enums.BookStatus;
import edu.miu.cs489.tsogt.backend.repository.BookRepo;
import edu.miu.cs489.tsogt.backend.service.impl.BookServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(value= MockitoJUnitRunner.class)
class IntegrationTests {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BookController bookController;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooksByUsername() {
        String username = "john_doe";
        BookResponce2 book1 = new BookResponce2(1, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Scribner", "A novel about the American Dream", 1925, BookStatus.AVAILABLE);
        BookResponce2 book2 = new BookResponce2(2, "To Kill a Mockingbird", "Harper Lee", "Classics", "HarperCollins", "A novel addressing racial injustice", 1960, BookStatus.AVAILABLE);

        List<BookResponce2> expectedBooks = Arrays.asList(book1, book2);

        when(bookService.getAllBooksByOwner(username)).thenReturn(expectedBooks);

        ResponseEntity<List<BookResponce2>> mockResponseEntity = ResponseEntity.ok(expectedBooks);
        when(bookController.searchBookById(username)).thenReturn(mockResponseEntity);

        ResponseEntity<List<BookResponce2>> responseEntity = bookController.searchBookById(username);
        Assert.assertEquals(expectedBooks, responseEntity.getBody());
    }

}
