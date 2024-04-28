package edu.miu.cs489.tsogt.backend.controller;


import edu.miu.cs489.tsogt.backend.dto.book.BookRequest;
import edu.miu.cs489.tsogt.backend.dto.book.BookResponce2;
import edu.miu.cs489.tsogt.backend.dto.transaction.TransactionRequest;
import edu.miu.cs489.tsogt.backend.dto.transaction.TransactionResponce;
import edu.miu.cs489.tsogt.backend.exception.transaction.RequestAlreadySentException;
import edu.miu.cs489.tsogt.backend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/project/api/v1/service/transaction/{username}")
    public ResponseEntity<List<TransactionResponce>> findByUsername(@PathVariable String username) {
        List<TransactionResponce> transactions = transactionService.getTransactionsByUser(username);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping(value = "/project/api/v1/service/transaction/{username}/borrower")
    public ResponseEntity<List<TransactionResponce>> findByUsernameBorrower(@PathVariable String username) {
        List<TransactionResponce> transactions = transactionService.getTransactionsByBorrower(username);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping(value = "/project/api/v1/service/transaction/{id}/reject")
    public ResponseEntity<TransactionResponce> rejectTransaction(@PathVariable int id) {
        TransactionResponce transactionResponce = transactionService.rejectTransaction(id);
        return ResponseEntity.ok(transactionResponce);
    }

    @PutMapping(value = "/project/api/v1/service/transaction/{id}/approve")
    public ResponseEntity<TransactionResponce> approveTransaction(@PathVariable int id) {
        TransactionResponce transactionResponce = transactionService.approveTransaction(id);
        return ResponseEntity.ok(transactionResponce);
    }

    @PostMapping(value = "/project/api/v1/service/transaction/")
    public ResponseEntity<TransactionResponce> addTransaction(@RequestBody TransactionRequest request) throws RequestAlreadySentException {
        TransactionResponce transactionResponce = transactionService.addTransaction(request);
        return ResponseEntity.ok(transactionResponce);
    }
}
