package edu.miu.cs489.tsogt.backend.repository;

import edu.miu.cs489.tsogt.backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Integer> {
}
