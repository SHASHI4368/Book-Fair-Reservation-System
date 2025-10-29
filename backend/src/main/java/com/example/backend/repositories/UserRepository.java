package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.models.User;
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
