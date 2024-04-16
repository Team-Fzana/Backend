package com.example.fzana.repository;

import com.example.fzana.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
