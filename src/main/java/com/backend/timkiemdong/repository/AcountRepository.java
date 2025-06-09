package com.backend.timkiemdong.repository;

import com.backend.timkiemdong.entity.Acount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcountRepository extends JpaRepository<Acount, Long> {
    Optional<Acount> findByEmail(String email);
    Boolean existsByEmail(String email);

}
