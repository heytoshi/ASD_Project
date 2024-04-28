package edu.miu.cs489.tsogt.backend.repository;

import edu.miu.cs489.tsogt.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    public abstract Optional<User> findByUsername(String username);
    public abstract Optional<User> findByEmail(String email);
    public abstract Optional<User> findByPhoneNumber(String phoneNumber);


}
