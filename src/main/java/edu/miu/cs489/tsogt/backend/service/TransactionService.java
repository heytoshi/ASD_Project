package edu.miu.cs489.tsogt.backend.service;

import edu.miu.cs489.tsogt.backend.dto.transaction.TransactionRequest;
import edu.miu.cs489.tsogt.backend.dto.transaction.TransactionResponce;
import edu.miu.cs489.tsogt.backend.exception.transaction.RequestAlreadySentException;
import edu.miu.cs489.tsogt.backend.model.User;

import java.util.List;

public interface TransactionService {
    List<TransactionResponce> getTransactionsByUser(String username);
    List<TransactionResponce> getTransactionsByBorrower(String username);

    TransactionResponce rejectTransaction(int id);
    TransactionResponce approveTransaction(int id);
    TransactionResponce addTransaction(TransactionRequest transaction) throws RequestAlreadySentException;
}
