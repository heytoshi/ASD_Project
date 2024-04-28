package edu.miu.cs489.tsogt.backend.model;

import edu.miu.cs489.tsogt.backend.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;
    private LocalDate approvalDate;
    @Column(nullable = false)
    private LocalDate pendingDate;
    private LocalDate completionDate;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne
    private Book ownerBook;

    @OneToOne
    private Book borrowerBook;

    public Transaction(TransactionStatus status, LocalDate approvalDate, LocalDate pendingDate, LocalDate completionDate, User borrower, User owner, Book ownerBook, Book borrowerBook) {
        this.status = status;
        this.approvalDate = approvalDate;
        this.pendingDate = pendingDate;
        this.completionDate = completionDate;
        this.borrower = borrower;
        this.owner = owner;
        this.ownerBook = ownerBook;
        this.borrowerBook = borrowerBook;
    }
}
