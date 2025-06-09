package com.backend.timkiemdong.repository;

import com.backend.timkiemdong.entity.Permission;
import com.backend.timkiemdong.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
//    Role findByName (ERole name);
//Set<Role> findByName(Set<String> name);
}
