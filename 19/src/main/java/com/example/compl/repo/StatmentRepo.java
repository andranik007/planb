package com.example.compl.repo;

import com.example.compl.Models.StatmentModel;
import com.example.compl.Models.StatmentStatus;
import com.example.compl.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatmentRepo extends JpaRepository<StatmentModel,Long> {
    List<StatmentModel> findByUser(UserModel userModel);
}
