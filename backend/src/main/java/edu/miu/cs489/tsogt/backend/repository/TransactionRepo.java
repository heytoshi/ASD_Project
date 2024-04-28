package edu.miu.cs489.tsogt.backend.repository;

import edu.miu.cs489.tsogt.backend.dto.transaction.TransactionRequest;
import edu.miu.cs489.tsogt.backend.model.Book;
import edu.miu.cs489.tsogt.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

    @Query(value = "select t from Transaction t where t.status = 0 and t.borrower.id = :id")
    Transaction checkIfRequestSent(int id);

    @Query(value = "select t from Transaction t where t.owner.username = :username")
    List<Transaction> findTransactionByUsername(String username);

    @Query(value = "select t from Transaction t where t.borrower.username = :username")
    List<Transaction> findTransactionByBorrowerUsername(String username);
}
