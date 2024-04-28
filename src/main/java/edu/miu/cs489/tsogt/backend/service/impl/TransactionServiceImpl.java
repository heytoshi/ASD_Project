package edu.miu.cs489.tsogt.backend.service.impl;

import edu.miu.cs489.tsogt.backend.dto.transaction.TransactionRequest;
import edu.miu.cs489.tsogt.backend.dto.transaction.TransactionResponce;
import edu.miu.cs489.tsogt.backend.enums.BookStatus;
import edu.miu.cs489.tsogt.backend.enums.TransactionStatus;
import edu.miu.cs489.tsogt.backend.exception.transaction.RequestAlreadySentException;
import edu.miu.cs489.tsogt.backend.model.Transaction;
import edu.miu.cs489.tsogt.backend.repository.BookRepo;
import edu.miu.cs489.tsogt.backend.repository.TransactionRepo;
import edu.miu.cs489.tsogt.backend.repository.UserRepo;
import edu.miu.cs489.tsogt.backend.service.TransactionService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final BookRepo bookRepo;
    private final UserRepo userRepo;
    private TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo, BookRepo bookRepo, UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<TransactionResponce> getTransactionsByUser(String username) {
        var transactions = transactionRepo.findTransactionByUsername(username);
        return mapTransactionsToResponse(transactions);
    }

    @Override
    public List<TransactionResponce> getTransactionsByBorrower(String username) {
        var transactions = transactionRepo.findTransactionByBorrowerUsername(username);
        return mapTransactionsToResponse(transactions);
    }

    @Override
    public TransactionResponce rejectTransaction(int id) {
        var transaction = transactionRepo.findById(id).orElse(null);
        transaction.setStatus(TransactionStatus.REJECTED);
        Transaction newTransaction = transactionRepo.save(transaction);
        return mapTransactionToResponse(newTransaction);
    }

    @Override
    public TransactionResponce approveTransaction(int id) {
        var transaction = transactionRepo.findById(id).orElse(null);
        transaction.setStatus(TransactionStatus.APPROVED);
        transaction.setApprovalDate(LocalDate.now());
        var ownerBook = bookRepo.findById(transaction.getOwnerBook().getId()).orElse(null);
        var borrowerBook = bookRepo.findById(transaction.getBorrowerBook().getId()).orElse(null);
        ownerBook.setStatus(BookStatus.BORROWED);
        borrowerBook.setStatus(BookStatus.BORROWED);
        bookRepo.saveAll(Arrays.asList(ownerBook, borrowerBook));
        Transaction newTransaction =  transactionRepo.save(transaction);
        return mapTransactionToResponse(newTransaction);
    }

    @Override
    public TransactionResponce addTransaction(TransactionRequest transaction) throws RequestAlreadySentException {
        var ownerUser = userRepo.findByUsername(transaction.ownerUsername()).orElse(null);
        var borrowerUser = userRepo.findByUsername(transaction.borrowerUsername()).orElse(null);
        var ownerBook = bookRepo.findById(transaction.ownerBookId()).orElse(null);
        var borrowerBook = bookRepo.findById(transaction.borrowerBookId()).orElse(null);

        var checkExist = transactionRepo.checkIfRequestSent(borrowerUser.getId());
        if(checkExist == null) {
            Transaction newTransaction = new Transaction(TransactionStatus.PENDING, null, LocalDate.now(),
                    null, borrowerUser, ownerUser, ownerBook, borrowerBook);
            transactionRepo.save(newTransaction);
            return mapTransactionToResponse(newTransaction);
        } else {
            throw new RequestAlreadySentException("Request Already Sent");
        }
    }


    private List<TransactionResponce> mapTransactionsToResponse(List<Transaction> transactions) {
        return transactions.stream().map(this::mapTransactionToResponse).collect(Collectors.toList());
    }

    private TransactionResponce mapTransactionToResponse(Transaction transaction) {
        return new TransactionResponce(
                transaction.getId(),
                transaction.getApprovalDate(),
                transaction.getPendingDate(),
                transaction.getCompletionDate(),
                transaction.getBorrower().getUsername(),
                transaction.getOwnerBook().getTitle(),
                transaction.getBorrowerBook().getTitle(),
                transaction.getStatus()
        );
    }
}
