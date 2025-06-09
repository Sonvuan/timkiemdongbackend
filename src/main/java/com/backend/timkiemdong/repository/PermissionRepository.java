package com.backend.timkiemdong.repository;

import com.backend.timkiemdong.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
//    Set<Permission> findByName(Set<String> name);
//Optional<Permission> findByName(Set<String> name);
}
