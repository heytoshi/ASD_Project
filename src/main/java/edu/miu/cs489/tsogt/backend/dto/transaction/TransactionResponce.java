package edu.miu.cs489.tsogt.backend.dto.transaction;

import edu.miu.cs489.tsogt.backend.enums.TransactionStatus;

import java.time.LocalDate;

public record TransactionResponce(
        int id,
        LocalDate approvalDate,
        LocalDate pendingDate,
        LocalDate completionDate,
        String borrower,
        String ownerBook,
        String borrowerBook,
        TransactionStatus status
) {
}
