package com.example.usermanagement.repository;

import com.example.usermanagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u ORDER BY u.id ASC")
    List<User> findAllUsersSorted();
}
