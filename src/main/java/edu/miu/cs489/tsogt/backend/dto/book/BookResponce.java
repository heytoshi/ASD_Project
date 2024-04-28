package edu.miu.cs489.tsogt.backend.dto.book;

import edu.miu.cs489.tsogt.backend.dto.user.UserResponce;
import edu.miu.cs489.tsogt.backend.enums.BookStatus;

public record BookResponce(
        int id,
        String title,
        String author,
        String genre,
        String publisher,
        String description,
        int year,
        BookStatus status,
        UserResponce owner
) {
}
