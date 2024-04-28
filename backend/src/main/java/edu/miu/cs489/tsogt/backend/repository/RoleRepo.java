package edu.miu.cs489.tsogt.backend.repository;

import edu.miu.cs489.tsogt.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
