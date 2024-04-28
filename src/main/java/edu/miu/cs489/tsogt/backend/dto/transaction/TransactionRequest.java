package edu.miu.cs489.tsogt.backend.dto.transaction;

public record TransactionRequest(
        String ownerUsername,
        String borrowerUsername,
        int ownerBookId,
        int borrowerBookId
) {
}
