package com.umland.dao;

import com.umland.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    // You can add custom query methods here if needed
    Optional<User> findByEmailAndPassword(String email, String password);

}