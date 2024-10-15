package com.muflikhan.backend.repositories;
import com.muflikhan.backend.entities.ERole;
import com.muflikhan.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
