package com.example.compl.repo;

import com.example.compl.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<UserModel,Long> {
    UserModel findByUsername(String username);
}
