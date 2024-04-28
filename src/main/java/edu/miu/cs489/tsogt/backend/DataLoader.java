package edu.miu.cs489.tsogt.backend;

import edu.miu.cs489.tsogt.backend.enums.BookStatus;
import edu.miu.cs489.tsogt.backend.enums.TransactionStatus;
import edu.miu.cs489.tsogt.backend.model.*;
import edu.miu.cs489.tsogt.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {
    private final BookRepo bookRepo;
    private final TransactionRepo transactionRepo;
    private final AddressRepo addressRepo;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(BookRepo bookRepo, TransactionRepo transactionRepo, AddressRepo addressRepo, RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.bookRepo = bookRepo;
        this.transactionRepo = transactionRepo;
        this.addressRepo = addressRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        roleRepo.saveAll(Arrays.asList(adminRole, userRole));

        Address address1 = new Address("123 Main St", "Anytown", "CA", 12345);
        Address address2 = new Address("456 Elm St", "Othercity", "NY", 54321);

        User user1 = new User("John", "Doe", "john.doe@example.com", passwordEncoder.encode("test1234"), "6412339999", "john_doe", true, true, true, true);
        User user2 = new User("Jane", "Doe", "jane.doe@example.com", passwordEncoder.encode("test1234"), "6412339988","jane_doe", true, true, true, true);

        user1.setRoles(Collections.singletonList(userRole));
        user2.setRoles(Collections.singletonList(userRole));

        user1.setAddress(address1);
        user2.setAddress(address2);

        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Scribner",
                "A novel about the American Dream", 1925, BookStatus.AVAILABLE, user1);

        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Classics", "HarperCollins",
                "A novel addressing racial injustice", 1960, BookStatus.AVAILABLE, user1);

        Book book3 = new Book("The Catcher in the Rye", "J.D. Salinger", "Literary Fiction", "Little, Brown and Company",
                "A coming-of-age novel", 1951, BookStatus.AVAILABLE, user2);

        Book book4 = new Book("1984", "George Orwell", "Dystopian Fiction", "Secker & Warburg",
                "A dystopian novel about totalitarianism", 1949, BookStatus.AVAILABLE, user2);


        userRepo.saveAll(Arrays.asList(user1, user2));


        bookRepo.saveAll(Arrays.asList(book1, book2, book3, book4));

        Transaction transaction = new Transaction(TransactionStatus.PENDING, null, LocalDate.now(),
                null, user1, user2, book4, book2);

        user1.setTransactionList(Arrays.asList(transaction));
        user2.setTransactionList(Arrays.asList(transaction));
        transactionRepo.save(transaction);

    }
}
