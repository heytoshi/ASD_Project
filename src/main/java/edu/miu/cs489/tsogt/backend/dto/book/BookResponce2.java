package edu.miu.cs489.tsogt.backend.dto.book;

import edu.miu.cs489.tsogt.backend.enums.BookStatus;

public record BookResponce2(
        int id,
        String title,
        String author,
        String genre,
        String publisher,
        String description,
        int year,
        BookStatus status
) {
}
