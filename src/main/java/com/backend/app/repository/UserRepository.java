// src/main/java/com/backend/app/repository/UserRepository.java
package com.backend.app.repository;

import com.backend.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuário pelo email
    Optional<User> findByEmail(String email); 

    // Buscar usuários que tenham "admin" igual a true ou false
    List<User> findByAdmin(boolean isAdmin);

    // Buscar usuários pelo nome (ou parte do nome) - case insensitive
    List<User> findByNameContainingIgnoreCase(String name);

    // Buscar todos os usuários com paginação
    Page<User> findAll(Pageable pageable);
}
