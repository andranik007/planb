package com.example.compl.Service;

import com.example.compl.Models.RoleModel;
import com.example.compl.Models.UserModel;
import com.example.compl.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void store(UserModel data) {
        UserModel user = new UserModel();
        user.setUsername(data.getUsername());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setName(data.getName());
        user.setSurname(data.getSurname());
        user.setPatronymic(data.getPatronymic());
        user.setNumberPhone(data.getNumberPhone());
        user.setEmail(data.getEmail());

        Set<RoleModel> roles = new HashSet<>();
        roles.add(RoleModel.USER);  // Default role
        if (data.getUsername().equals("ADMIN")) {
            roles.add(RoleModel.ADMIN);  // Add admin role for admin user
        }
        user.setRoles(roles);

        userRepo.save(user);
    }
}
